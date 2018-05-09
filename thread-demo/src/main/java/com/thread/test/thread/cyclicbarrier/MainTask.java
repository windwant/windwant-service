package com.thread.test.thread.cyclicbarrier;

public class MainTask implements Runnable {

    public void run() {
        try {
            System.out.println("mian task begin");
            for (int i = 0; i < 5; i++) {
                Thread.sleep(1000);
                System.out.println("============" + i + "============");
            }
            System.out.println("mian task implemented");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

