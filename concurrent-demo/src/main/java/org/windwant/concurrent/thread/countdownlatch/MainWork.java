package org.windwant.concurrent.thread.countdownlatch;

/**
 * Created by Administrator on 18-5-9.
 */
public class MainWork implements Runnable {

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


