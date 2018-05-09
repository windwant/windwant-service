package com.thread.test.thread.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class SubTask implements Runnable{

    private CyclicBarrier cb;

    private int seconds;

    private String taskName;

    public SubTask(CyclicBarrier cb, int seconds, String taskName){
        this.cb = cb;
        this.seconds = seconds;
        this.taskName = taskName;
    }

    public void run() {
        try{
            System.out.println("subtask " + taskName + " begin, need time: " + seconds + "s");
            long b = System.currentTimeMillis();
            for (int i = 0; i < seconds; i++) {
                Thread.sleep(1000);
                System.out.println("subtask: " + taskName + "============" + i + "============");
            }
            long d = System.currentTimeMillis() - b;
            System.out.println("subtask " + taskName + " over, executing time: " + TimeUnit.SECONDS.convert(d, TimeUnit.MILLISECONDS));
            cb.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
