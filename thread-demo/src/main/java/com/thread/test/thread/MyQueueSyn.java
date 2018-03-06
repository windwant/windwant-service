package com.thread.test.thread;

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
            add("a");
            add("b");
            add("c");
            add("d");
            add("e");
            add("f");
            add("g");
        }};

        CR cr = new CR(preQueue);
        cr.addEle(String.valueOf(ThreadLocalRandom.current().nextInt(100)));
        cr.start();

        new CRP(cr).start();

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

class CRP extends Thread{

    private CR cr;

    public CRP(CR t){
        cr = t;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cr.addEle(String.valueOf(i * i));
            if(i > ThreadLocalRandom.current().nextInt(100)) {
                cr.tify();
            }
            System.out.println("mian thread add cr queue ele: " + i);
        }
    }
}

class CR extends Thread{

    public void addEle(String ele) {
        synchronized (queue) {
            queue.add(ele);
        }
    }

    public void tify(){
        synchronized (queue){
            queue.notify();
        }
    }

    public CR(){}

    public CR(Queue queue){
        this.queue = queue;
    }

    public Queue<String> queue = new ArrayDeque<>();

    @Override
    public void run() {
        while (true){
            synchronized (queue){
                try {
                    if(queue.size() == 0){
                        System.out.println("cr thread queue wait...");
                        queue.wait();
                    }
                    Thread.sleep(1000);
                    System.out.println("cr thread queue poll ele: " + queue.poll());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
