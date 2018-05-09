package com.thread.test.thread.threadpoolexecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolExecutor
 * 通过线程池执行所提交的任务的ExecutorService，通常由Executors生成
 * 执行高并发任务比较高效，因为减少了任务的穿行等待时间，同时很好的管理着执行需求的资源，包括线程，
 * 通常，维护者一些基础的任务执行数据，例如已完成任务数量
 *
 * ThreadPoolExecutor有许多可调正的参数，可以适用于不同的用途，但是通常我们使用
 * Executors#newCachedThreadPool 无容量限制，线程自动回收
 * Executors#newFixedThreadPool 固定容量线程池
 * Executors#newSingleThreadExecutor 单线程
 * 等为许多通用场景预置了很多参数，
 *
 * Hello world!
 *
 */
public class ThreadPoolFactoryTest
{
    public static void main( String[] args )
    {
        /**
         * @ int corePoolSize：线程池中维护的线程数量，生命周期同线程池，除非设置了allowCoreThreadTimeOut
         * @ int maximumPoolSize：允许的最大数量
         * @ long keepAliveTime：允许的最大存活时间
         * @ TimeUnit unit：单位
         * @ BlockingQueue<Runnable> workQueue：存储等待执行任务，execute提交的Runnable类型任务
         * @ RejectedExecutionHandler handler：线程阻塞，队列已满时执行的操作 ---饱和策略
         */
        ExecutorService enew = new ThreadPoolExecutor(5, 20, 0L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
        new RejectedExecutionHandler() {
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println("this is the exception execution begin");
                        executor.execute(r);
                        System.out.println("this is the exception execution end");
                    }
                });

        for (int i = 0; i < 100; i++) {
            enew.execute(new Runnable() {
                public void run() {
                    int l = ThreadLocalRandom.current().nextInt();
                    System.out.println("task..." + l + "begin");
                    try {
                        Thread.sleep(2000);
                        System.out.println("task..." + l + "end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        System.out.println("add end...");
    }
}
