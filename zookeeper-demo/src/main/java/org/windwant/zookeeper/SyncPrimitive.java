package org.windwant.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;

public class SyncPrimitive implements Watcher {

    public ZooKeeper zk = null;
    public Integer mutex;

    SyncPrimitive() {
        mutex = new Integer(-1);
    }

    /**
     * 初始化zookeeper
     * @param domain
     */
    protected void initZK(String domain){
        System.out.println(Thread.currentThread().getName() + ": init zookeeper...");
        try {
            zk = new ZooKeeper(domain, 30000, this);
            System.out.println(Thread.currentThread().getName() + ": zookeeper connected " + zk);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化应用根节点 并发处理
     * @param root
     */
    protected synchronized void initZKRootNode(String root){
        try {
            if(zk != null) {
                if (zk.exists(root, false) != null) {
                    List<String> child = zk.getChildren(root, false);
                    if (child != null && !child.isEmpty()) {
                        child.forEach(c -> {
                            try {
                                zk.delete(root + "/" + c, -1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (KeeperException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                    zk.setData(root, String.valueOf(0).getBytes(), -1);
                    System.out.println(Thread.currentThread().getName() + ": app root node " + root + " init success! ");
                }else {
                    zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,
                            CreateMode.PERSISTENT);
                    System.out.println(Thread.currentThread().getName() + ": app root node " + root + " create success! ");
                }
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void process(WatchedEvent event) {
        if(event.getState().equals(Event.KeeperState.SyncConnected)) {
        }
    }
}