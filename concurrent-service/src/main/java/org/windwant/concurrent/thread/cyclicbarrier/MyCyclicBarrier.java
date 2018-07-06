package org.windwant.concurrent.thread.cyclicbarrier;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

