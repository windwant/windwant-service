package com.thread.test.thread.waitnotify;

import java.util.concurrent.ThreadLocalRandom;

class Producer extends Thread{

    private Consumer consumer;

    public Producer(Consumer consumer){
        this.consumer = consumer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            consumer.addEle(String.valueOf(i * i));
            if(i > ThreadLocalRandom.current().nextInt(100)) {
                consumer.cnotify();
            }
            System.out.println("mian thread add cr queue ele: " + i);
        }
    }
}

