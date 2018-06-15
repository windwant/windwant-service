package org.windwant.zookeeper.master_workers;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * master role
 * manage task worker
 * Created by Administrator on 18-6-14.
 */
public class Master extends Unit implements Runnable{
    private ZooKeeper zk;
    private int liveTime; //set thread live time，simulates master down

    public Master(){}

    public Master(int liveTime){
        this.liveTime = liveTime;
    }

    @Override
    public void run() {
        threadName = UUID.randomUUID().toString();
        prefix = threadName + ": ";
        initZK();
        tryBeMaster();
    }

    /**
     * get free workers
     */
    public void getWorkers(){
        List<String> tempWorkers = new ArrayList<>();
        zk.getChildren(MWConstants.WORKER_FREE_NODE_PATH, event -> {
            if (event.getType().equals(Watcher.Event.EventType.NodeChildrenChanged)
                    || event.getType().equals(Watcher.Event.EventType.NodeDataChanged)) {
                getWorkers();//node changed trigger watch again
            }
        }, (rc, path, ctx, children, stat) -> {
            if (rc == 0 && children != null) {
                MWConstants.setWorkers(children);//cache locally
                log("current available workers " + MWConstants.getWorkers().toString());
            }
        }, tempWorkers);
    }

    /**
     * init zookeeper
     */
    public void initZK(){
        try {
            zk = new ZooKeeper(MWConstants.ZK_HOST + ":" + MWConstants.ZK_PORT, 3000, null);
            log("init zookeeper");
            //ROOT NODE
            synchronized (MWConstants.MASTER_OK) {
                if (zk.exists(MWConstants.MW_ROOT, false) == null) {
                    zk.create(MWConstants.MW_ROOT,
                            threadName.getBytes(),
                            ZooDefs.Ids.OPEN_ACL_UNSAFE,
                            CreateMode.PERSISTENT);
                    log("create " + MWConstants.MASTER_NODE_PATH);
                }

//                if (zk.exists(MWConstants.MASTER_NODE_PATH, false) != null) {
//                    deleteNode(zk, MWConstants.MASTER_NODE_PATH);
//                }
//                if (zk.exists(MWConstants.WORKER_NODE_PATH, false) != null) {
//                    deleteNode(zk, MWConstants.WORKER_NODE_PATH);
//                }
//                if (zk.exists(MWConstants.TASK_NODE_PATH, false) != null) {
//                    deleteNode(zk, MWConstants.TASK_NODE_PATH);
//                }
//                if (zk.exists(MWConstants.ASSIGN_NODE_PATH, false) != null) {
//                    deleteNode(zk, MWConstants.ASSIGN_NODE_PATH);
//                }
                log("zookeeper init success !");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    /**
     * try to be master node
     */
    public void tryBeMaster(){
        //MASTER NODE
        try {
            //模拟先后
            int t = ThreadLocalRandom.current().nextInt(1, 5) * 1000;
            Thread.sleep(t);
            //set NodeDeleted watch, if master node deleted, try to be master again
            zk.exists(MWConstants.MASTER_NODE_PATH, event -> {
                if (event.getType().equals(Watcher.Event.EventType.NodeDeleted)) {
                    tryBeMaster();
                }
            });

            //create master node, if exists, throw exception
            zk.create(MWConstants.MASTER_NODE_PATH,
                    threadName.getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL);
            log("MASTER init success!");
            //simulate run down
            if(liveTime != -1) {
                for (int i = 0; i < liveTime/1000; i++) {
                    log("i am working well!");
                    Thread.sleep(1000);
                }
                log("MASTER down after " + liveTime + ", for test !");
                zk.close();
            }else {
                log("MASTER used for work!");
                //make sure on process do this one time
                synchronized (MWConstants.MASTER_OK){
                    //workers
                    if (zk.exists(MWConstants.WORKER_NODE_PATH, false) == null) {
                        zk.create(MWConstants.WORKER_NODE_PATH,
                                threadName.getBytes(),
                                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                                CreateMode.PERSISTENT);
                        log("create " + MWConstants.WORKER_NODE_PATH);
                        zk.create(MWConstants.WORKER_BUSY_NODE_PATH,
                                threadName.getBytes(),
                                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                                CreateMode.PERSISTENT);
                        log("create " + MWConstants.WORKER_BUSY_NODE_PATH);
                        zk.create(MWConstants.WORKER_FREE_NODE_PATH,
                                threadName.getBytes(),
                                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                                CreateMode.PERSISTENT);
                        log("create " + MWConstants.WORKER_FREE_NODE_PATH);
                    }

                    //tasks
                    if (zk.exists(MWConstants.TASK_NODE_PATH, false) == null) {
                        zk.create(MWConstants.TASK_NODE_PATH,
                                threadName.getBytes(),
                                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                                CreateMode.PERSISTENT);
                        log("create " + MWConstants.TASK_NODE_PATH);
                    }

                    //assigns
                    if (zk.exists(MWConstants.ASSIGN_NODE_PATH, false) == null) {
                        zk.create(MWConstants.ASSIGN_NODE_PATH,
                                threadName.getBytes(),
                                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                                CreateMode.PERSISTENT);
                        log("create " + MWConstants.ASSIGN_NODE_PATH);
                    }
                    //notifications
                    if (zk.exists(MWConstants.TASK_STATUS_NOTIFICATION, false) == null) {
                        zk.create(MWConstants.TASK_STATUS_NOTIFICATION,
                                threadName.getBytes(),
                                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                                CreateMode.PERSISTENT);
                        log("create " + MWConstants.TASK_STATUS_NOTIFICATION);
                    }
                }
            }
            //get available workers
            getWorkers();
            //task management
            assignTask();
        } catch (KeeperException e) {
            if(e instanceof KeeperException.NodeExistsException){
                log("master exists, wait and will try...");
            }else {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * assign comming tasks to awailable workers
     */
    public synchronized void assignTask(){
        //set task NodeChildrenChanged watch
        List<String> children = null;
        try {
            zk.getData(MWConstants.TASK_NODE_PATH, event -> {
                if (event.getType().equals(Watcher.Event.EventType.NodeDataChanged)) {
                    assignTask();
                }
            }, new Stat());

            children = zk.getChildren(MWConstants.TASK_NODE_PATH, false);

        if (children == null) return;
        MWConstants.setTasks(children);
        //delete tasks already got
        for (int i = 0; i < children.size(); i++) {
            try {
                zk.delete(MWConstants.TASK_NODE_PATH + "/" + children.get(i), -1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (KeeperException e) {
                e.printStackTrace();
            }
        };
        if (MWConstants.getWorkers().size() == 0) {
            log("no available workers !");
            return;
        }
        for (int i = 0; i < MWConstants.getTasks().size(); i++) {
            int wi = ThreadLocalRandom.current().nextInt(0, MWConstants.getWorkers().size());
            int ti = ThreadLocalRandom.current().nextInt(0, MWConstants.getTasks().size());
            try {
                zk.create(MWConstants.ASSIGN_NODE_PATH + "/" + MWConstants.getWorkers().get(wi),
                        MWConstants.getTasks().get(ti).getBytes(),//set task name to assigns/worker data
                        ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.EPHEMERAL);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log("assign task " + MWConstants.getTasks().get(ti) + " to worker " + MWConstants.getWorkers().get(wi));
            //remove worker item from local cache
            MWConstants.getWorkers().remove(wi);
            //once task assigned, delete its node
            MWConstants.getTasks().remove(ti);
        }
        assignTask();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if(zk != null){
            try {
                zk.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void destory() throws Throwable {
        if(zk != null){
            zk.close();
        }
    }

    public static void main(String[] args) {
        Master master = new Master();
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                try {
                    master.destory();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        new Thread(new Master(-1)).start();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
