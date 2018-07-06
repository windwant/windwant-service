package org.windwant.concurrent.thread.blockingqueue;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Administrator on 18-5-9.
 */
public class MySynchronousCus implements Runnable{

    private BlockingQueue<String> sq;

    private int period = 1000;

    public MySynchronousCus(BlockingQueue sq, int period){
        this.sq = sq;
        this.period = period;
    }

    public void run() {
        try{
            while (true){
                Thread.sleep(period);
                String value = sq.take();
                System.out.println("cus take value: " + value + " synchronous :" + sq.toString());
                System.out.println("======================================================");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
