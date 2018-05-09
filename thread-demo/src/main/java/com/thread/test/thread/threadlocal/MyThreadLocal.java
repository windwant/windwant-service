package com.thread.test.thread.threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by windwant on 2016/5/29.
 */
public class MyThreadLocal {
    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        MyThreadLocalRunnable runnable = new MyThreadLocalRunnable();
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);
        es.execute(t1);
        es.execute(t2);
        es.execute(t3);

        es.shutdown();
    }
}

