package com.thread.test.thread.blockingqueue;

import java.util.Random;
import java.util.concurrent.DelayQueue;

/**
 * 延迟队列测试生产者
 *
 * Created by Administrator on 18-5-9.
 */
public class MyDelayPro implements Runnable{

    private DelayQueue<MyDelayItem> dq;

    private int period = 1000;

    private Random r = new Random();

    public MyDelayPro(DelayQueue dq, int period){
        this.dq = dq;
        this.period = period;
    }
    public void run() {
        try{
            while (true){
                Thread.sleep(period);
                if(dq.size() > 5){
                    continue;
                }
                MyDelayItem di = new MyDelayItem(r.nextInt(10), r.nextInt(10));
                dq.offer(di);
                System.out.println("delayqueue: add---" + di.toString() + "size: " + dq.size());
                System.out.println("*************************************");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}