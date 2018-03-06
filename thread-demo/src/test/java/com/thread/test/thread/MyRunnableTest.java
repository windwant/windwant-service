package com.thread.test.thread;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple ThreadPoolFactoryTest.
 */
public class MyRunnableTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MyRunnableTest(String testName)
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( MyRunnableTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        MyRunnableSyn mr = new MyRunnableSyn();
        new Thread(mr).start();
        new Thread(mr).start();
        new Thread(mr).start();
    }
}
