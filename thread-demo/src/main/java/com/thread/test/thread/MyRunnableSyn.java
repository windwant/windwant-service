package com.thread.test.thread;
/**
 * Created by windwant on 2016/5/23.
 */
public class MyRunnableSyn implements Runnable{
    private int ticket = 5;
    public synchronized void  run() {
        try {
            while (ticket > 0){
                Thread.sleep(1000);
                System.out.println("ticket: " + ticket);
                ticket--;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyRunnableSyn mr = new MyRunnableSyn();
        Thread t = new Thread(mr);
        Thread t1 = new Thread(mr);
        Thread t2 = new Thread(mr);
        t.start();
        t1.start();
        t2.start();
    }
}
