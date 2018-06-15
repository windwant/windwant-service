package org.windwant.zookeeper.master_workers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Administrator on 18-6-14.
 */
public class MWConstants {
    public static final String ZK_HOST = "localhost";

    public static final int ZK_PORT = 2181;

    public static final String MW_ROOT = "/MW";
    public static final String MASTER_NODE_PATH = MW_ROOT + "/master";

    public static final String WORKER_NODE_PATH = MW_ROOT + "/workers";

    public static final String WORKER_BUSY_NODE_PATH = WORKER_NODE_PATH + "/busy";

    public static final String WORKER_FREE_NODE_PATH = WORKER_NODE_PATH + "/free";

    public static final String TASK_NODE_PATH = MW_ROOT + "/tasks";

    public static final String ASSIGN_NODE_PATH = MW_ROOT + "/assigns";

    public static final String TASK_STATUS_NOTIFICATION = MW_ROOT + "/notifications";

    public static AtomicBoolean MASTER_OK = new AtomicBoolean(false);

    public static List<String> getWorkers() {
        return workers;
    }

    public static void setWorkers(List<String> workers) {
        MWConstants.workers = workers;
    }

    public static List<String> getTasks() {
        return tasks;
    }

    public static void setTasks(List<String> tasks) {
        if(tasks == null || tasks.isEmpty()) return;
        tasks.stream().forEach(task->{
            if(!MWConstants.getTasks().contains(task)){
                MWConstants.getTasks().add(task);
            };
        });
        MWConstants.tasks = tasks;
    }

    //available workers
    private static List<String> workers = new ArrayList<>();
    private static List<String> tasks = new ArrayList<>();

    public static void main(String[] args) {
        setWorkers(new ArrayList(){{add("abb");}});
        System.out.println(getWorkers().toString());
        getWorkers().remove(0);
        System.out.println(getWorkers().toString());
    }
}
