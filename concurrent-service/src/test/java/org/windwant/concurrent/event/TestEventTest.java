package org.windwant.concurrent.event;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Unit test for simple ThreadPoolFactoryTest.
 */
public class TestEventTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TestEventTest(String testName)
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TestEventTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TestEvent t = new TestEvent("hello", "this is the applicationevent msg");
        ac.publishEvent(t);
    }
}
