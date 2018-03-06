package com.thread.test.thread;

import java.util.Random;
import java.util.concurrent.*;

/**
 * CountDownLatch
 * 同步策略 允许一个或多个线程等待一些列其它线程操作完成后执行
 * 由给定count初始化
 * await方法等待所有等待线程countDown方法执行之后释放
 * count不能重置，只能使用一次 对比CyclicBarrier
 * 多用途同步工具：
 * 初始化为1的CountDownLatch可以作为 on/off开关；所有线程等待直到所有线程都执行了countDown gate效果
 * 初始化为N....
 *
 * 必须等待所有线程都执行完成后才能让所有线程继续执行时其比较有用的特性
 *
 * Created by windwant on 2016/5/27.
 */
public class MyCountDownLatch {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        CountDownLatch cd = new CountDownLatch(5);
        Random r = new Random();
        es.execute(new SubWork(cd, r.nextInt(10), "task1"));
        es.execute(new SubWork(cd, r.nextInt(10), "task2"));
        es.execute(new SubWork(cd, r.nextInt(10), "task3"));
        es.execute(new SubWork(cd, r.nextInt(10), "task4"));
        es.execute(new SubWork(cd, r.nextInt(10), "task5"));
        cd.await();
        es.execute(new MainWork());
        es.shutdown();
    }


}

class MainWork implements Runnable {

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

class SubWork implements Runnable{

    private CountDownLatch cd;

    private int seconds;

    private String taskName;

    SubWork(CountDownLatch cd, int seconds, String taskName){
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
