package org.windwant.zookeeper.master_workers;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.UUID;

/**
 * post task
 * monitor task
 * Created by Administrator on 18-6-14.
 */
public class Clients extends Unit implements Runnable {
    private ZooKeeper zk;

    @Override
    public void run() {
        threadName = UUID.randomUUID().toString();
        prefix = threadName + ": ";
        initZK();
        for (int i = 0; i < 100 ; i++) {
            request();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * init zookeeper
     */
    public void initZK(){
        try {
            zk = new ZooKeeper(MWConstants.ZK_HOST + ":" + MWConstants.ZK_PORT, 3000, null);
            log("init zookeeper");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * post task request
     */
    public void request(){
        //create task node
        zk.create(MWConstants.TASK_NODE_PATH + "/" + threadName,
                new byte[0],
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT_SEQUENTIAL,
                (rc, path, ctx, name) -> {
                    log("client request post task " + name);
                    try {
                        Stat s = zk.exists(MWConstants.TASK_NODE_PATH, false);
                        zk.setData(MWConstants.TASK_NODE_PATH, new byte[0], s.getVersion());
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    watchTask(name); //monitor status
                }, new Object());

    }

    /**
     * monitor task status
     * @param task
     */
    private void watchTask(String task){
        try {
            zk.exists(MWConstants.TASK_STATUS_NOTIFICATION + "/" + task.substring(task.lastIndexOf("/") + 1), event -> {
                if (event.getType().equals(Watcher.Event.EventType.NodeCreated)) {
                    log("task begin...");
                    watchTask(task);//to watch task complete
                } else if (event.getType().equals(Watcher.Event.EventType.NodeDeleted)) {
                    log("task complete!");
                }
            });
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(new Clients()).start();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
