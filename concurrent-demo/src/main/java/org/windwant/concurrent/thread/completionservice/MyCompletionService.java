package org.windwant.concurrent.thread.completionservice;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by windwant on 2016/11/22.
 */
public class MyCompletionService {
    static class CTask implements Callable{

        @Override
        public Object call() throws Exception {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(1000);
                System.out.println("thread name: " + Thread.currentThread().getName());
            }
            return ThreadLocalRandom.current().nextInt(100);
        }
    }

    //CompletionService 集中处理Future get()
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletionService cs = new ExecutorCompletionService(Executors.newFixedThreadPool(3));
        for (int i = 0; i < 5; i++) {
            cs.submit(new MyCompletionService.CTask());
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(cs.take().get());
        }
    }
}
