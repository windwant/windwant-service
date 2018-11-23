package org.windwant.concurrent.thread.completionservice;

import java.util.concurrent.*;

/**
 * 解耦异步任务的提交与结果消费
 * 结果获取顺序依据任务的完成顺序
 *
 * Created by windwant on 2016/11/22.
 */
public class MyCompletionService {
    static class CTask implements Callable{

        @Override
        public Object call() throws Exception {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(1000);
                System.out.println("thread: " + Thread.currentThread().getName() + " executing... ");
            }
            return ThreadLocalRandom.current().nextInt(100);
        }
    }

    //CompletionService 集中处理Future get()
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        CompletionService cs = new ExecutorCompletionService(service);
        //集中提交任务
        for (int i = 0; i < 5; i++) {
            cs.submit(new MyCompletionService.CTask());
        }

        //结果获取
        for (int i = 0; i < 5; i++) {
            System.out.println(cs.take().get());
        }
        service.shutdown();
    }
}
