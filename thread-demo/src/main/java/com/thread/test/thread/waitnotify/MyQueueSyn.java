package com.thread.test.thread.waitnotify;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Thread Object wait() notify()基本
 * Created by windwant on 2016/11/29.
 */
public class MyQueueSyn {

    public static void main(String[] args) throws InterruptedException {
        Queue preQueue = new ArrayDeque<String>() {{
            add("a"); add("b"); add("c"); add("d"); add("e"); add("f"); add("g");
        }};

        Consumer cr = new Consumer(preQueue);
        cr.addEle(String.valueOf(ThreadLocalRandom.current().nextInt(100)));
        cr.start();

        new Producer(cr).start();

//        for (int i = 0; i < 100; i++) {
//            Thread.sleep(1000);
//            cr.addEle(String.valueOf(i * i));
//            if(i > ThreadLocalRandom.current().nextInt(100)) {
//                cr.tify();
//            }
//            System.out.println("mian thread add cr queue ele: " + i);
//        }
    }
}

