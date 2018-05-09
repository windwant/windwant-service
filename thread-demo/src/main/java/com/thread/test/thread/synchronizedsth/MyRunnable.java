package com.thread.test.thread.synchronizedsth;

/**
 * Created by windwant on 2016/5/23.
 */
public class MyRunnable implements Runnable{
    private int ticket = 5;
    private MyService myService;

    public void setMs(MyService myService) {
        this.myService = myService;
    }

    public void  run() {
        try {
            if(myService != null){
                myService.lockPrintNum(5, Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyRunnable mr = new MyRunnable();
        mr.setMs(new MyService());
        Thread t = new Thread(mr);
        Thread t1 = new Thread(mr);
        Thread t2 = new Thread(mr);
        t.start();
        t1.start();
        t2.start();
    }
}
