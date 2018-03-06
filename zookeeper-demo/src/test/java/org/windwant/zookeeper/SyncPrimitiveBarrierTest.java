package org.windwant.zookeeper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class SyncPrimitiveBarrierTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SyncPrimitiveBarrierTest(String testName)
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SyncPrimitiveBarrierTest.class );
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

    }

    /**
     * Rigourous Test :-)
     */
    public void testSyncPrimitveBarrier()
    {
        SyncPrimitiveBarrier syncPrimitiveBarrier;
        try{
            //够三个开始leave
            syncPrimitiveBarrier = new SyncPrimitiveBarrier("192.168.7.162", "/pnodeb", 3);
            boolean flag = syncPrimitiveBarrier.enter();
            System.out.println("Entered barrier: " + flag);
            if(!flag) System.out.println("Error when entering the barrier");


            Thread.sleep(5000);
            syncPrimitiveBarrier.leave();
            System.out.println("Left barrier");
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
