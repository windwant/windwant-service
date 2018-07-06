package org.windwant.zookeeper.master_workers;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * worker role
 * execute task
 * Created by Administrator on 18-6-14.
 */
public class Worker extends Unit implements Runnable {
    private ZooKeeper zk;

    @Override
    public void run() {
        threadName = UUID.randomUUID().toString();
        prefix = threadName + ": ";
        initZK();
        acceptTask();
    }

    /**
     * init zookeeper
     */
    public void initZK(){
        try {
            zk = new ZooKeeper(MWConstants.ZK_HOST + ":" + MWConstants.ZK_PORT, 3000, null);
            log("init zookeeper");
            zk.create(MWConstants.WORKER_FREE_NODE_PATH + "/" + threadName,
                    "0".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL);
            log("register worker node!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    public void destory() throws Throwable {
        if(zk != null){
            zk.close();
        }
    }

    /**
     * accept new task
     */
    public void acceptTask(){
        //one time one deal task check
        synchronized (MWConstants.MASTER_OK) {
            String asign = MWConstants.ASSIGN_NODE_PATH + "/" + threadName;
            //set task assign watch, if assigned, get and execute task
            zk.exists(asign, event -> {
                        if (event.getType().equals(Watcher.Event.EventType.NodeCreated)) {
                            acceptTask();
                        }
                    },
                    (rc, path, ctx, stat) -> {
                        if (rc != 0) return;
                        byte[] btask = new byte[0];
                        try {
                            //get task name from assigns/worker data
                            btask = zk.getData(MWConstants.ASSIGN_NODE_PATH + "/" + threadName, false, null);
                            //once accept assign, delete assign node
                            zk.delete(asign, -1);
                            String task = new String(btask, "utf-8");
                            log("worker accept task " + task);
                            //create task status node
                            zk.create(MWConstants.TASK_STATUS_NOTIFICATION + "/" + task,
                                    threadName.getBytes(),
                                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                                    CreateMode.PERSISTENT);
                            //move worker from free to busy
                            zk.delete(MWConstants.WORKER_FREE_NODE_PATH + "/" + threadName, -1);
                            zk.create(MWConstants.WORKER_BUSY_NODE_PATH + "/" + threadName,
                                    threadName.getBytes(),
                                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                                    CreateMode.PERSISTENT);
                            //simulate task execute process
                            int workTime = ThreadLocalRandom.current().nextInt(3, 8);
                            for (int i = 0; i < workTime; i++) {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                log("worker is running task " + task);
                            }
                            //task finished, move worker from busy to free
                            zk.delete(MWConstants.WORKER_BUSY_NODE_PATH + "/" + threadName, -1);
                            zk.create(MWConstants.WORKER_FREE_NODE_PATH + "/" + threadName,
                                    threadName.getBytes(),
                                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                                    CreateMode.PERSISTENT);
                            //delete task status node
                            zk.delete(MWConstants.TASK_STATUS_NOTIFICATION + "/" + task, -1);
                            log("work finish task " + task);
                            //retry accept new task
                            acceptTask();
                        } catch (KeeperException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }, new Object());
        }
    }

    public static void main(String[] args) {
        Worker worker = new Worker();
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                try {
                    worker.destory();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        new Thread(worker).start();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
