package org.windwant.concurrent.thread.threadpoolexecutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 18-5-9.
 */
public class MyThreadPoolExecutor extends ThreadPoolExecutor {

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
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
