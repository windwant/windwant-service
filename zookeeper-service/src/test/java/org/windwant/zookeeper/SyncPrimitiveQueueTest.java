package org.windwant.zookeeper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.zookeeper.KeeperException;

/**
 * Unit test for simple App.
 */
public class SyncPrimitiveQueueTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SyncPrimitiveQueueTest(String testName)
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SyncPrimitiveQueueTest.class );
    }

    SyncPrimitiveQueue syncPrimitiveQueue;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        syncPrimitiveQueue = new SyncPrimitiveQueue("192.168.7.162", "/pnode", 10);
    }

    /**
     * Rigourous Test :-)
     */
    public void testSyncPrimitveQueueProducer()
    {
        try {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                syncPrimitiveQueue.produce(i);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testSyncPrimitveQueueConsumer()
    {
        try {
            for (int i = 0; i < Integer.MAX_VALUE ; i++) {
                syncPrimitiveQueue.consume();
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
