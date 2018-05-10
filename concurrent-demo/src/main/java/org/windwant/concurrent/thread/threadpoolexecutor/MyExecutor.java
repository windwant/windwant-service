package org.windwant.concurrent.thread.threadpoolexecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 自定义pre after terminated事件
 * Created by windwant on 2016/12/22.
 */
public class MyExecutor {

    public static void main(String[] args) {
        ExecutorService es = new MyThreadPoolExecutor(5, 20, 0L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
        es.execute(()-> System.out.println("this is the task process info....."));
        es.shutdown();
    }
}

