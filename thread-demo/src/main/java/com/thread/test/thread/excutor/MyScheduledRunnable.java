package com.thread.test.thread.excutor;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 18-5-9.
 */
public class MyScheduledRunnable implements Runnable{
    private int num = 0;
    public MyScheduledRunnable(int num){
        this.num = num;
    }
    public void run() {
        ReentrantLock lock = new ReentrantLock();
        try{
            lock.lock();
            for (int i = 0; i < num; i++) {
                System.out.println("current thread: " + Thread.currentThread().getName() + " num--" + i);
                Thread.sleep(1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
