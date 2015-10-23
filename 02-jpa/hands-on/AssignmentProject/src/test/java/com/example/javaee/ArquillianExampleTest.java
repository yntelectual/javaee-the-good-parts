package com.example.javaee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import com.example.javaee.cdi.Beacon;
import com.example.javaee.dao.AuthorDAO;
import com.example.javaee.dao.BookDAO;
import com.example.javaee.dao.PublisherDAO;
import com.example.javaee.dao.ReviewDAO;
import com.example.javaee.model.Address;
import com.example.javaee.model.Author;
import com.example.javaee.model.Book;
import com.example.javaee.model.Publisher;
import com.example.javaee.model.Review;

@RunWith(Arquillian.class)
public class ArquillianExampleTest {

    private static final String TEST_ISBN_2 = "1538231549796";
    /**
     * In Arquillian tests, we can fully use CDI.
     */
    @Inject
    private BookDAO bookDAO;
    @Inject
    private ReviewDAO reviewDAO;
    @Inject
    private AuthorDAO authorDAO;
    @Inject
    private PublisherDAO publishDAO;

    @Inject
    private Beacon beacon;
    @Inject
    private Logger logger;

    /**
     * Define what is deployed during the test to the application server.
     *
     * The persistence.xml file is needed of course for the persistence unit definition. A datasource is not needed, since we can now use
     * the new default datasource available in +JEE7+.
     * 
     */
    @Deployment
    public static WebArchive createDeployment() {
        File[] mavenDependencies = Maven.configureResolver().fromFile("src/test/resources/settings-clean-test.xml").loadPomFromFile("pom.xml").importCompileAndRuntimeDependencies().resolve().withoutTransitivity().asFile();
        WebArchive war = ShrinkWrap.create(WebArchive.class)
        // deploying everything in out app, in future, you might be more restrictive, e.g. deploy only models and EJBs.
        .addPackages(true, "com.example.javaee")
        // deploy test specific persistence xml
        .addAsResource("persistence_test.xml", "META-INF/persistence.xml")
        // deploy additional maven libraries
        .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml").addAsLibraries(mavenDependencies);
        System.out.println("Test archive contents: " + war.toString(true));
        return war;
    }

    /**
     * Optional: the JUnit watcher that logs test case invocations so that you can easily find them in logs.
     */
    @Rule
    public TestWatcher currentTestWatcher = new TracingTestWatcher();

    private static final String TEST_ISBN = UUID.randomUUID().toString();

    /**
     * The tests should be executed in the order defined by @InSequence annotation. All the test methods in this class will be executed on
     * the same DB. so what you insert/modify in the first test case is the starting state for test #2.
     * 
     * In order to refer to some entity created in test #1, u can use for example a static ref to Id - see TEST_ISBN
     * 
     */
    @Test
    @InSequence(1)
    public void testDumbCheck() {
        try {
            beacon.sanityCheck();
        } catch (Exception e) {
            fail("Check the TODOs, Are you even reading this?");
        }
    }

    @Test
    @InSequence(1)
    public void testSimpleSave() {

        Author firstAuthor = new Author();
        firstAuthor.setName("Johny Clever");
        firstAuthor.setAddress(new Address("Smarttown", "Smart st.", "12345", "Smartland"));
        firstAuthor.setDateOfBirth(new Date(0));// 1.1.1970
        authorDAO.persist(firstAuthor);
        assertNotNull("Author should have been saved in DB", firstAuthor.getId());
        // test entity callbacks
        assertNotNull("Author should have a creation date in Entity event", firstAuthor.getCreationDate());
        assertTrue("Author should have an age calculated in Entity event", firstAuthor.getAge() > 0);
        assertTrue("Author should have an address filled", firstAuthor.getAddress().getStreet().length() > 0);
        try {
            firstAuthor.getBooks().size();
            fail("books should be lazy loaded");
        } catch (Exception e) {
            logger.info("book access failed, but it is ok, we excepted that");
        }

        Publisher publisher = new Publisher();
        publisher.setAddress(new Address("Corptown", "Money printing St.2", "12322", "Moneyland"));
        publishDAO.persist(publisher);
        assertNotNull("publisher has not been saved in DB ", publisher.getId());
    }

    @Test
    @InSequence(2)
    public void testBookAuthorSave() {

        Author firstAuthor = authorDAO.getAll().get(0);
        Publisher firstPublisher = publishDAO.getAll().get(0);

        Book b = new Book();
        b.setIsbn(TEST_ISBN);
        b.setName("How to stop sucking at life and started to love testable code");
        b.setPublishDate(new Date());
        // Streams vs existing api
        // Set<String> tags2 = new HashSet<>(Arrays.asList(new String[] { "programming", "tech", "bestseller" }));
        Set<String> tags = Arrays.stream(new String[] { "programming", "tech", "bestseller" }).collect(Collectors.toSet());
        b.setTags(tags);
        b.setAuthor(firstAuthor);
        b.setPublisher(firstPublisher);
        logger.info("***********start saving book***********");
        bookDAO.persist(b);
        logger.info("***********end saving book***********");
        assertNotNull("book should have had a creation date set", b.getCreationDate());
        logger.info("***********start get book***********");
        Book savedBook = bookDAO.getById(TEST_ISBN);
        logger.info("***********end get book***********");
        assertNotNull("there should a book in the DB", savedBook);
        assertTrue("book should have three tags", savedBook.getTags().size() == 3);
        assertTrue("book should have laodedFromDB == true", savedBook.isLoadedFromDB());
        assertNotNull("book should have an author", savedBook.getAuthor());
        assertEquals("it is not our author", savedBook.getAuthor().getId(), firstAuthor.getId());
        assertNotNull("book should have a publisher", savedBook.getPublisher());
        logger.info("***********start load author ***********");
        firstAuthor = authorDAO.loadById(firstAuthor.getId());
        logger.info("***********end load author ***********");
        try {
            firstAuthor.getBooks().size();
        } catch (Exception e) {
            logger.error("books were not loaded!", e);
            fail("author books should have been loaded.");
        }
        assertTrue("author should have a single book", firstAuthor.getBooks().size() == 1);

    }

    @Test
    @InSequence(3)
    public void testLazyLoad() {
        logger.info("***********start get book***********");
        Book bookFromDB = bookDAO.getById(TEST_ISBN);
        logger.info("***********end get book***********");
        assertNotNull("The book has to saved in DB ", bookFromDB);
        assertNotNull("book has to have an author and should be laoded eagerly", bookFromDB.getAuthor());
        assertTrue("book should have eagerly laoded tags", !bookFromDB.getTags().isEmpty());
        try {
            bookFromDB.getReviews().size();
            fail("book review should be laoded lazily");
        } catch (Exception e) {
            logger.info("book reviews not laoded, but that is fine!");
        }
        try {
            bookFromDB.getPublisher().getAddress();
            fail("book publisher should be laoded lazily");
        } catch (Exception e) {
            logger.info("book publisher not laoded, but that is fine!");
        }
        logger.info("***********start load book***********");
        // now we load it and everything shuld be included
        bookFromDB = bookDAO.loadById(TEST_ISBN);
        logger.info("***********end load book***********");
        assertNotNull("The book has to saved in DB ", bookFromDB);
        assertNotNull("book has to have an author and should be laoded eagerly", bookFromDB.getAuthor());
        assertTrue("book should have eagerly laoded tags", !bookFromDB.getTags().isEmpty());
        assertTrue("book review should be laoded now", bookFromDB.getReviews().size() > -1);
        assertNotNull("book publisher should be now laoded", bookFromDB.getPublisher().getAddress());

    }

    @Test
    @InSequence(4)
    public void testInverseSave() {
        List<Author> authors = authorDAO.getAll();
        assertTrue("there should be one author in DB", authors.size() == 1);
        Author a = authors.get(0);
        logger.info("***********start load author ***********");
        a = authorDAO.loadById(a.getId());
        logger.info("***********end load author ***********");
        assertNotNull("author not loaded", a);
        Book existingBook = bookDAO.loadById(TEST_ISBN);
        Book newBook = new Book();
        newBook.setAuthor(a);
        newBook.setPublisher(existingBook.getPublisher());
        newBook.setName("Path of a testing ninja");
        newBook.setIsbn(TEST_ISBN_2);
        a.getBooks().add(newBook);
        logger.info("***********start author update ***********");
        a = authorDAO.update(a);
        logger.info("***********start author update ***********");
        Book newBookFromDB = bookDAO.loadById(TEST_ISBN_2);
        assertNotNull("new book not saved :(", newBookFromDB);
        assertNotNull("new book does not have an author ", newBookFromDB.getAuthor());
    }

    @Test
    @InSequence(5)
    public void testReviewsAndQuery() {
        // an orphanated review
        Review orphan = new Review();
        orphan.setFrom("haha");
        try {
            reviewDAO.persist(orphan);
            fail("you should not be able to persist an orphan to the DB");
        } catch (Exception e) {
            
        }
        
        Book existingBook = bookDAO.loadById(TEST_ISBN);
        Book existingBook2 = bookDAO.loadById(TEST_ISBN_2);
        Review r = new Review();
        r.setBook(existingBook);
        r.setFrom("shameless troll");
        // testing your DB schema - length of column text
        r.setText(generateDummyString(999));
        reviewDAO.persist(r);

        assertNotNull("review is not saved,", r.getId());

        Review r2 = new Review();
        r2.setBook(existingBook2);
        r.setText(generateDummyString(999));
        reviewDAO.persist(r2);

        assertNotNull("review is not saved,", r.getId());

        List<Review> reviewsForBook1 = reviewDAO.getAllForBook(TEST_ISBN);
        assertTrue("reviews for book 1 are not what they should be", reviewsForBook1.size() == 1);
        List<Review> reviewsForBook2 = reviewDAO.getAllForBook(TEST_ISBN_2);
        assertTrue("reviews for book 2 are not what they should be", reviewsForBook2.size() == 1);
        
       Book bookFromDb = bookDAO.getById(TEST_ISBN);
       //reviews should not be loaded now
       try {
           bookFromDb.getReviews().size();
           fail("reviews have to be laoded lazily");
       } catch (Exception e){
           
       }
       bookFromDb = bookDAO.loadById(TEST_ISBN);
       //reviews should now contain 1 item
       assertEquals("Book 1 should have 1 review ",1, bookFromDb.getReviews().size());

    }

    private String generateDummyString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append("d");
        }
        return sb.toString();
    }
}
