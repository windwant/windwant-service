package com.thread.test.thread;

/**
 * Created by windwant on 2016/5/23.
 */
public class MyRunnable implements Runnable{
    private int ticket = 5;
    private MySTestService ms;

    public void setMs(MySTestService ms) {
        this.ms = ms;
    }

    public void  run() {
        try {
            if(ms != null){
                ms.lockPrintNum(5, Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyRunnable mr = new MyRunnable();
        mr.setMs(new MySTestService());
        Thread t = new Thread(mr);
        Thread t1 = new Thread(mr);
        Thread t2 = new Thread(mr);
        t.start();
        t1.start();
        t2.start();
    }
}
