package org.windwant.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SyncPrimitiveBarrier extends SyncPrimitive {

    private String root;
    //屏障阈值
    private int size;
    private String name;

    /**
     * Barrier constructor
     *
     * @param domain
     * @param root
     * @param size
     */
    public SyncPrimitiveBarrier(String domain, String root, int size) {
        super();
        this.root = root;
        this.size = size;

        initZK(domain);
        initZKRootNode(root);

        // My node name
        try {
            name = new String(InetAddress.getLocalHost().getCanonicalHostName().toString());
        } catch (UnknownHostException e) {
            System.out.println(e.toString());
        }

    }

    /**
     * Join barrier
     *
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */

    boolean enter() throws KeeperException, InterruptedException{
        List<String> list = zk.getChildren(root, true);
        //当前节点数小于阈值，则创建节点，进入barrier
        if (list.size() < size) {
            System.out.println("node: " + list.size());
            this.name = zk.create(root + "/" + name, new byte[0], Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);
            if(list.size() + 1 == size){
                System.out.println("set data node size" + list.size());
                zk.setData(root, String.valueOf(list.size() + 1).getBytes(), -1);
            }
            System.out.println(Thread.currentThread().getName() + ": " + name + " enter barrier!");
            return true;
        }else {
            //否则不进入
            System.out.println(Thread.currentThread().getName() + ": " + name + " barrier full, inaccessible!");
            return false;
        }
    }

    /**
     * Wait until all reach barrier
     *
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */

    boolean leave() throws KeeperException, InterruptedException, UnsupportedEncodingException {
        while (true) {
            int data = Integer.parseInt(new String(zk.getData(root, false, new Stat()), "UTF-8"));
            if (data == size) {
                System.out.println("leave size: " + data);
                //离开
                zk.delete(name, -1);
                System.out.println(Thread.currentThread().getName() + ": " + name + " left barrier!");
                return true;
            } else {
                System.out.println(Thread.currentThread().getName() + ": " + name + " waitting for others!");
                Thread.sleep(1000);//每秒检查一次
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                SyncPrimitiveBarrier syncPrimitiveBarrier = new SyncPrimitiveBarrier("localhost:2181", "/barrier_test", 3);
                boolean flag = false;
                try {
                    //模拟需要到达barrier的时间
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1,5)*1000);
                    flag = syncPrimitiveBarrier.enter();

                    //尝试离开barrier
                    syncPrimitiveBarrier.leave();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }).start();

        }
    }
}