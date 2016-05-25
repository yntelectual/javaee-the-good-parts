package com.example.javaee.SampleProjectJAXRS;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

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

import com.example.javaee.SampleProjectJAXRS.dao.BookDAO;
import com.example.javaee.SampleProjectJAXRS.model.Book;

@RunWith(Arquillian.class)
public class ArquillianExampleTest {

    /**
     * In Arquillian tests, we can fully use CDI.
     */
    @Inject
    private BookDAO bookDao;

    /**
     * Define what is deployed during the test to the application server.
     *
     * The persistence.xml file is needed of course for the persistence unit definition. A datasource is not needed, since we can now use
     * the new default datasource available in +JEE7+.
     * 
     */
    @Deployment
    public static WebArchive createDeployment() {
        File[] mavenDependencies = Maven.resolver().loadPomFromFile("pom.xml").importCompileAndRuntimeDependencies().resolve().withoutTransitivity().asFile();
        WebArchive war = ShrinkWrap.create(WebArchive.class)
        // deploying everything in out app, in future, you might be more restrictive, e.g. deploy only models and EJBs.
        .addPackages(true, "com.example.javaee.")
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
    public void testDao() {
        assertNotNull("CDI should work", bookDao);
    }

}
