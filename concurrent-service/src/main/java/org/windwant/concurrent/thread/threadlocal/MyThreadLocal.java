package org.windwant.concurrent.thread.threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal 每个线程独立版本
 * Created by windwant on 2016/5/29.
 */
public class MyThreadLocal {
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(3);
        MyThreadLocalRunnable runnable = new MyThreadLocalRunnable();

        for (int i = 0; i < 3; i++) {
            es.submit(runnable);
        }

        es.shutdown();
    }
}

