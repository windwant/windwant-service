package com.thread.test.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by windwant on 2016/5/29.
 */
public class MyThreadLocal {
    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        MyTL mtl = new MyTL();
        es.execute(mtl);
        es.execute(mtl);
        es.execute(mtl);
        es.execute(mtl);
        es.execute(mtl);
        es.shutdown();
    }
}

class MyTL implements Runnable
{

    private static ThreadLocal<Integer> num = new ThreadLocal<Integer>() {
        public Integer initialValue() {
            return 0;
    }
    };

    public void run() {
        synchronized (this){
            while (true) {
                Integer tmp = num.get();
                tmp++;
                num.set(tmp);
                System.out.println("Current thread: " + Thread.currentThread().getName() + "  My num: " + num.get());
            }
        }
    }
}
