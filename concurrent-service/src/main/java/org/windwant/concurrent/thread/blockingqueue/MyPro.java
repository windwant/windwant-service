package org.windwant.concurrent.thread.blockingqueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Administrator on 18-5-9.
 */
public class MyPro implements Runnable{

    private BlockingQueue<String> bq;

    private int period = 1000;

    private Random r = new Random();
    public MyPro(BlockingQueue bq, int period){
        this.bq = bq;
        this.period = period;
    }

    public void run() {
        try{
            while (true){
                Thread.sleep(period);
                String value = String.valueOf(r.nextInt(100));
                if(bq.offer(value)){ //offer 能够插入就返回true，否则返回false
                    System.out.println("pro make value: " + value + " queue : " + bq.toString());
                    System.out.println("******************************************************");
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}