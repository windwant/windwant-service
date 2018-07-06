package org.windwant.concurrent.thread.waitnotify;

import java.util.ArrayDeque;
import java.util.Queue;

class Consumer extends Thread{

    public void addEle(String ele) {
        synchronized (queue) {
            queue.add(ele);
        }
    }

    public void cnotify(){
        synchronized (queue){
            queue.notify();
        }
    }

    public Consumer(){}

    public Consumer(Queue queue){
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
