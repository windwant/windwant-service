package org.windwant.concurrent.thread.blockingqueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * 测试消费者
 */
public class MyCus implements Runnable{

    private BlockingQueue<String> bq;

    private int period = 1000;

    private Random r = new Random();
    public MyCus(BlockingQueue bq, int period){
        this.bq = bq;
        this.period = period;
    }

    public void run() {
        try{
            while (true){
                Thread.sleep(period);
                String value = bq.take(); //获取队列头部元素，无元素则阻塞
                System.out.println("cus take value: " + value + " queue : " + bq.toString());
                System.out.println("======================================================");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}