package org.windwant.concurrent.thread.blockingqueue;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;


/**
 * 阻塞队列测试生产者
 */
public class MySynchronousPro implements Runnable{

    private SynchronousQueue<String> sq;

    private int period = 1000;

    private Random r = new Random();
    public MySynchronousPro(SynchronousQueue sq, int period){
        this.sq = sq;
        this.period = period;
    }

    public void run() {
        try{
            while (true){
                Thread.sleep(period);
                String value = String.valueOf(r.nextInt(100));
                if(sq.offer(value)) {
                    System.out.println("pro make value: " + value + " synchronous :" + sq.toString());
                    System.out.println("******************************************************");
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

