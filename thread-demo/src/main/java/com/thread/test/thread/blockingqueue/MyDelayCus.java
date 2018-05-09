package com.thread.test.thread.blockingqueue;

import java.util.concurrent.DelayQueue;

/**
 * 延迟队列测试消费者
 * Created by Administrator on 18-5-9.
 */
public class MyDelayCus implements Runnable{

    private DelayQueue<MyDelayItem> dq;

    private int period = 1000;

    public MyDelayCus(DelayQueue dq, int period){
        this.dq = dq;
        this.period = period;
    }
    public void run() {
        try{
            while (true){
                Thread.sleep(period);
                MyDelayItem di = dq.take();
                System.out.println("delayqueue: remove---" + di.toString());
                System.out.println("delayqueue: ---" + dq.toString());
                System.out.println("======================================");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
