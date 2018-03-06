package com.thread.test.thread;

import java.util.Random;
import java.util.concurrent.*;

/**
 * CyclicBarrier
 * 同步工具：允许一组线程共同等待一个壁垒点
 * 适用于固定数量线程的同步
 * 等待线程释放后可以重复使用
 *
 * Created by windwant on 2016/5/27.
 */
public class MyCyclicBarrier {
    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        CyclicBarrier cb = new CyclicBarrier(5, new MainTask());//MainTask可选
        Random r = new Random();
        es.execute(new SubTask(cb, r.nextInt(10), "task1"));
        es.execute(new SubTask(cb, r.nextInt(10), "task2"));
        es.execute(new SubTask(cb, r.nextInt(10), "task3"));
        es.execute(new SubTask(cb, r.nextInt(10), "task4"));
        es.execute(new SubTask(cb, r.nextInt(10), "task5"));
        es.shutdown();
    }
}

class MainTask implements Runnable {

    public void run() {
        try {
            System.out.println("mian task begin");
            for (int i = 0; i < 5; i++) {
                Thread.sleep(1000);
                System.out.println("============" + i + "============");
            }
            System.out.println("mian task implemented");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

class SubTask implements Runnable{

    private CyclicBarrier cb;

    private int seconds;

    private String taskName;

    SubTask(CyclicBarrier cb, int seconds, String taskName){
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
