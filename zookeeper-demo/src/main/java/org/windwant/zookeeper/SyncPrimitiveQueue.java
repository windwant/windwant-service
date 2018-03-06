package org.windwant.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import java.nio.ByteBuffer;
import java.util.List;
/**
 * Producer-Consumer queue
 */
public class SyncPrimitiveQueue extends SyncPrimitive {

    String root;

    /**
     * Constructor of producer-consumer queue
     *
     * @param address
     * @param name
     */

    SyncPrimitiveQueue(String address, String name) {
        super(address);
        this.root = name;
        // Create ZK node name
        if (zk != null) {
            try {
                Stat s = zk.exists(root, false);
                if (s == null) {
                    zk.create(root, new byte[0], Ids.OPEN_ACL_UNSAFE,
                            CreateMode.PERSISTENT);
                }
            } catch (KeeperException e) {
                System.out
                        .println("Keeper exception when instantiating queue: "
                                + e.toString());
            } catch (InterruptedException e) {
                System.out.println("Interrupted exception");
            }
        }
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
        System.out.println("produce value: " + node);
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
                    System.out.println("Going to wait");
                    mutex.wait();
                } else {
                    list.sort((String s1, String s2) -> s1.compareTo(s2)); //消费序号最小的节点
                    String dest = list.get(0);
//                    Integer min = new Integer(dest.replaceAll(".*[a-zA-Z]", ""));
//                    for(String s : list){
//                        Integer tempValue = new Integer(s.replaceAll(".*[a-zA-Z]", ""));
//                        if(tempValue < min){
//                            min = tempValue;
//                            dest = s;
//                        }
//                    }
//                    System.out.println("cosume value: " + root + "/" + dest);
//                    byte[] b = zk.getData(root + "/" + dest,
//                            false, stat);
//                    zk.delete(root + "/" + dest, 0);
                    System.out.println("cosume value: " + root + "/" + dest);
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
}