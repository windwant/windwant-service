package org.windwant.zookeeper.master_workers;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 18-6-14.
 */
public class MasterWorkersTasks {
    private static ConcurrentHashMap<String, Thread> masters = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, Integer> masterTime = new ConcurrentHashMap();

    MasterWorkersTasks(){
    }
    private static ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(10);

    public static void registerMaster(Thread master, int liveTime){
        masters.put(master.getName(), master);
        masterTime.put(master.getName(), liveTime);
        scheduled.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println(master.getName() + " MASTER down after " + liveTime + " !");
                master.interrupt();
            }
        }, liveTime, TimeUnit.MILLISECONDS);
    }

}
