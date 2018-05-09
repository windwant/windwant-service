package com.thread.test.thread.synchronizedsth;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by windwant on 2016/5/25.
 */
public class MyService {
    private int ticket = Integer.MAX_VALUE;

    public synchronized void synPrintNum(int num, String currentThreadName) throws InterruptedException {
        if(1 > num){
            System.out.println("num positive needed!");
            return;
        }

        for (int i = 0; i < num ; i++) {
            System.out.println(currentThreadName + "---current num: " + i);
            Thread.sleep(1000);
        }
    }

    public void synBlockPrintNum(int num, String currentThreadName) throws InterruptedException {
        if(1 > num){
            System.out.println("num positive needed!");
            return;
        }

        for (int i = 0; i < num ; i++) {
            Thread.sleep(1000);
            synchronized (this) {
                System.out.println(currentThreadName + "---current num: " + i);
                Thread.sleep(1000);
            }
        }
    }

    public void lockPrintNum(int num, String currentThreadName) throws InterruptedException {
        if(1 > num){
            System.out.println("num positive needed!");
            return;
        }

        Lock lock = new ReentrantLock();
        for (int i = 0; i < num ; i++) {
            Thread.sleep(1000);
            try{
                lock.lock();
                System.out.println(currentThreadName + "---current num: " + i);
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }

    public void lockHasInterrupt() throws InterruptedException {
        Lock lock = new ReentrantLock();
        lock.lockInterruptibly();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            try{
                lock.lock();
                System.out.println("current lock num: " + i);
                Thread.sleep(1000);
                ticket--;
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }
}
