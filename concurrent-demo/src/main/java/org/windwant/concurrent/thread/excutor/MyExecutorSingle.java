package org.windwant.concurrent.thread.excutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by windwant on 2016/12/22.
 */
public class MyExecutorSingle {

    public static void main(String[] args) {
        testSingle();
    }

    public static void testSingle(){
        Future future = null;
        ExecutorService es = null;
        try {
            es = Executors.newSingleThreadExecutor();
            future = es.submit(() -> {
                System.out.println("task begin...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("task end!!!");
            });
            System.out.println("task submit...");
            System.out.println(future.cancel(false));

            ThreadPoolExecutor tpe = (ThreadPoolExecutor) es;
            tpe.setRejectedExecutionHandler(new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    executor.execute(r);//把丢弃的任务直接执行
                }
            });
        }finally {
            if(es != null){
                es.shutdown();//soft close
            }
        }
    }
}
