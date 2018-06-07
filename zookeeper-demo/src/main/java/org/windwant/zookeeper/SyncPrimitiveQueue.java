package org.windwant.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Producer-Consumer queue
 */
public class SyncPrimitiveQueue extends SyncPrimitive {

    String root;

    /**
     * Constructor of producer-consumer queue
     *
     * @param domain
     * @param name
     */

    SyncPrimitiveQueue(String domain, String name) {
        super();
        this.root = name;
        initZK(domain);
        initZKRootNode(root);
    }

    /**
     * Add element to the queue.
     *
     * @param i
     * @return
     */

    boolean produce(int i) throws KeeperException, InterruptedException{
        ByteBuffer b = ByteBuffer.allocate(4);
        byte[] value;

        b.putInt(i);
        value = b.array();
        String node = zk.create(root + "/element", value, Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT_SEQUENTIAL);
        System.out.println(Thread.currentThread().getName() + ": produce value: " + node);
        synchronized (mutex){
            mutex.notifyAll();//通知消费
        }
        return true;
    }


    /**
     * Remove first element from the queue.
     *
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    int consume() throws KeeperException, InterruptedException{
        int retvalue = -1;
        Stat stat = null;

        // Get the first element available
        while (true) {
            synchronized (mutex) {
                List<String> list = zk.getChildren(root, true);
                if (list.size() == 0) {
                    System.out.println(Thread.currentThread().getName() + ": resource not awailable, waitting for produce!");
                    mutex.wait();
                } else {
                    list.sort((String s1, String s2) -> s1.compareTo(s2)); //消费序号最小的节点
                    String dest = list.get(0);
                    System.out.println(Thread.currentThread().getName() + ": cosume value: " + root + "/" + dest);
                    byte[] b = zk.getData(root + "/" + dest,
                            false, stat);
                    zk.delete(root + "/" + dest, 0); //消费后删除
                    ByteBuffer buffer = ByteBuffer.wrap(b);
                    retvalue = buffer.getInt();

                    return retvalue;
                }
            }
        }
    }

    public static void main(String[] args) {
        SyncPrimitiveQueue syncPrimitiveQueue = new SyncPrimitiveQueue("localhost:2181", "/queue_test");
        //生产 每隔三秒 模拟慢生产
        new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                try {
                    syncPrimitiveQueue.produce(i);
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1, 3)*1000);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //消费 每隔一秒 模拟快消费
        new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE ; i++) {
                try {
                    syncPrimitiveQueue.consume();
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1, 3)*1000);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}