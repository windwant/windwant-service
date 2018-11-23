package org.windwant.concurrent.thread.blockingdeque;

import java.util.Deque;
import java.util.concurrent.*;

/**
 * 双端队列测试
 * Created by windwant on 2016/5/27.
 */
public class MyBlockingDeque {

    public static void main(String[] args) throws InterruptedException {
        testArrayBlockingDeque();
    }

    public static void testArrayBlockingDeque() throws InterruptedException {
        BlockingDeque<String> deque = new LinkedBlockingDeque<String>();

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            deque.add(String.valueOf(ThreadLocalRandom.current().nextInt(100, 1999)));
        }, 0, 500, TimeUnit.MILLISECONDS);

        while (true){
            int interval = ThreadLocalRandom.current().nextInt(100, 900);
            System.out.println("\r\ninterval: " + interval);
            System.out.println("deque: ");
            System.out.println(printDeque(deque));

            Thread.sleep(interval);
            if(ThreadLocalRandom.current().nextBoolean()){
                System.out.println("i am taken from head deque: " + deque.pollFirst(10, TimeUnit.SECONDS));
            }else{
                System.out.println("i am taken from tail deque: " + deque.pollLast(10, TimeUnit.SECONDS));
            }
        }
    }

    public static String printDeque(Deque deque){
        StringBuilder sb = new StringBuilder();
        deque.stream().forEach(item-> sb.append("      | " + item + " |\r\n"));
        return sb.toString();
    }
}
