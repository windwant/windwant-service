package com.thread.test.thread.blockingqueue;

import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Administrator on 18-5-9.
 */
public class MyPriorityCus implements Runnable{

    private PriorityBlockingQueue<MyPriorityItem> pbq;

    private int period = 1000;

    private Random r = new Random();

    public MyPriorityCus(PriorityBlockingQueue pbq, int period){
        this.pbq = pbq;
        this.period = period;
    }
    public void run() {
        try{
            while (true){
                Thread.sleep(period);
                MyPriorityItem di = pbq.take();
                System.out.println("PriorityBlockingQueue: remove---" + di.toString());
                System.out.println("PriorityBlockingQueue: ---" + pbq.toString());
                System.out.println("======================================");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
