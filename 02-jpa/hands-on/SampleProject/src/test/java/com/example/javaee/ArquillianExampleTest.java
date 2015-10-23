package com.example.javaee;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;

import com.example.javaee.dao.CarDAO;
import com.example.javaee.dao.FlatDAO;
import com.example.javaee.dao.UserDAO;
import com.example.javaee.model.Car;
import com.example.javaee.model.Flat;
import com.example.javaee.model.User;

/**
 * With every test class a new deployment is created.
 * @author Matus Majchrak
 *
 */
@RunWith(Arquillian.class)
public class ArquillianExampleTest {

    /**
     * In Arquillian tests, we can fully use CDI.
     */
    @Inject
    private UserDAO userDao;
    @Inject
    private CarDAO carDao;
    @Inject
    private FlatDAO flatDao;

    /**
     * Define what is deployed during the test to the application server.
     *
     * The persistence.xml file is needed of course for the persistence unit definition. A datasource is not needed, since we can now use
     * the new default datasource available in +JEE7+.
     * 
     */
    @Deployment
    public static WebArchive createDeployment() {
        File[] mavenDependencies = Maven.configureResolver().fromFile("").loadPomFromFile("pom.xml").importCompileAndRuntimeDependencies().resolve().withoutTransitivity().asFile();
        WebArchive war = ShrinkWrap.create(WebArchive.class)
        // deploying everything in out app, in future, you might be more restrictive, e.g. deploy only models and EJBs.
        .addPackages(true, "com.example.javaee")
        // deploy test specific persistence xml
        .addAsResource("persistence_test.xml", "META-INF/persistence.xml")
        // deploy additional maven libraries
        .addAsLibraries(mavenDependencies);
        System.out.println("Test archive contents: " + war.toString(true));
        return war;
    }

    /**
     * Optional: the JUnit watcher that logs test case invocations so that you can easily find them in logs.
     */
    @Rule
    public TestWatcher currentTestWatcher = new TestWatcher() {

        protected void starting(org.junit.runner.Description description) {
            System.out.println("---- starting test case: " + description.getClassName() + "." + description.getMethodName());
        };

        protected void finished(org.junit.runner.Description description) {
            System.out.println("---- sucessful test case: " + description.getClassName() + "." + description.getMethodName());
        };

        protected void failed(Throwable e, org.junit.runner.Description description) {
            System.out.println("---- failed test case: " + description.getClassName() + "." + description.getMethodName() + " resone: " + e.getMessage());
        };
    };

    @Test
    public void testSimpleSave() {
        User u = new User();
        u.setName("test");
        userDao.save(u);
        assertNotNull("User should have a generated ID", u.getId());
        List<User> allItems = userDao.getAll();
        assertTrue("There should be 1 item in the DB", !allItems.isEmpty());
    }
    
    @Test
    public void testLazyLoading() {
        User u = new User();
        u.setName("test");
        userDao.save(u);
        assertNotNull("User should have a generated ID", u.getId());
        Car car = new Car();
        car.setModel("car1");
        carDao.save(car);
        assertNotNull("Car should have a generated ID", car.getId());
        //both are already in DB 
        Set<Car> cars = new HashSet<>();
        cars.add(car);
        u.setCars(cars);
        userDao.update(u);
        u = userDao.getById(u.getId());
        try {
            u.getCars().size();
            fail("lazy loaded collection has not been initialized, and the test should fail");;
        } catch (Exception e) {
            //no JPA exception:( lazi loading exceptionas are vendor specific
            assertTrue("Should have thrown loading exception", e.getMessage().contains("lazily"));
        }
        //override Lazy loading with jpoin fetch
        u = userDao.loadById(u.getId());
        assertTrue("lazy loaded collections should have been initialized", u.getCars().size() != -1);
    }
    
    @Test
    public void testSaveOnetoManyWithoutCascade() {
        User u = new User();
        u.setName("test");
        userDao.save(u);
        assertNotNull("User should have a generated ID", u.getId());
        Car car = new Car();
        car.setModel("car1");
        carDao.save(car);
        assertNotNull("Car should have a generated ID", car.getId());
        //both are already in DB 
        car.setUser(u);
        Set<Car> cars = new HashSet<>();
        cars.add(car);
        u.setCars(cars);
        userDao.update(u);
        //load state from DB 
        u = userDao.loadById(u.getId());
        //no relationship info has been stored in DB!
        assertTrue("No relationship info should have been stored in DB",u.getCars().isEmpty());
        car.setUser(u);
        //Car is the owner of the relationship, updating it should store the FK in DB 
        carDao.update(car);
        u = userDao.loadById(u.getId());
        assertTrue("Relationship should have been stored.", !u.getCars().isEmpty());
    }
    
    @Test
    public void testSaveOnetoManyWithCascade() {
        User u = new User();
        u.setName("test");
        userDao.save(u);
        assertNotNull("User should have a generated ID", u.getId());
        Flat f = new Flat();
        f.setAddress("streed country state");
        flatDao.save(f);
        assertNotNull("Flat should have a generated ID", f.getId());
        //both are already in DB 
        f.setUser(u);
        Set<Flat> flats = new HashSet<>();
        flats.add(f);
        u.setFlats(flats);
        userDao.update(u);
        //load state from DB 
        u = userDao.loadById(u.getId());
        //relationship should be stored, due to cascaded merge!
        assertTrue("No relationship info havs been stored in DB", !u.getFlats().isEmpty());
    }


}
