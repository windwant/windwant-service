package com.thread.test.thread.blockingdeque;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by windwant on 2016/5/27.
 */
public class MyBlockingDeque {

    public static void main(String[] args) throws InterruptedException {
        testArrayBlockingDeque();
    }

    public static void testArrayBlockingDeque() throws InterruptedException {
        BlockingDeque<String> deque = new LinkedBlockingDeque<String>();

        deque.addFirst("1");
        System.out.println(deque.toString());
        deque.addLast("2");
        System.out.println(deque.toString());

        String two = deque.takeLast();
        System.out.println(two);
        String one = deque.takeFirst();
        System.out.println(one);
    }
}
