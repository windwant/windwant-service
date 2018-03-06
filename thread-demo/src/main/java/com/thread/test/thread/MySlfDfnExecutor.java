package com.thread.test.thread;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义pre after terminated事件
 * Created by windwant on 2016/12/22.
 */
public class MySlfDfnExecutor{

    public static void main(String[] args) {
        ExecutorService es = new MYTPE(5, 20, 0L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        es.execute(()->{
            System.out.println("this is the task process info.....");
        });
        es.shutdown();
    }
}

class MYTPE extends ThreadPoolExecutor {

    public MYTPE(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        System.out.println(Thread.currentThread().getName() + ": beforeExecute");
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        System.out.println(Thread.currentThread().getName() + ": afterExecute");
    }

    @Override
    protected void terminated() {
        super.terminated();
        System.out.println(Thread.currentThread().getName() + ": terminated");
    }
}

class MYES extends AbstractExecutorService {

    @Override
    public void shutdown() {

    }

    @Override
    public List<Runnable> shutdownNow() {
        return null;
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }
}
