package com.thread.test.thread.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 18-5-9.
 */
public class SubWork implements Runnable{

    private CountDownLatch cd;

    private int seconds;

    private String taskName;

    public SubWork(CountDownLatch cd, int seconds, String taskName){
        this.cd = cd;
        this.seconds = seconds;
        this.taskName = taskName;
    }

    public void run() {
        try{
            System.out.println("subwork " + taskName + " begin, need time: " + seconds + "s");
            long b = System.currentTimeMillis();
            for (int i = 0; i < seconds; i++) {
                Thread.sleep(1000);
                System.out.println("subtask: " + taskName + "============" + i + "============");
            }
            long d = System.currentTimeMillis() - b;
            System.out.println("subwork " + taskName + " over, executing time: " + TimeUnit.SECONDS.convert(d, TimeUnit.MILLISECONDS));
            cd.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
