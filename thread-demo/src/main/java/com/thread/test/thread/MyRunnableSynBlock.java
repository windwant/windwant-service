package com.thread.test.thread;

/**
 * Created by windwant on 2016/5/23.
 */
public class MyRunnableSynBlock implements Runnable{
    private int ticket = 5;
    private MySTestService ms;

    public void setMs(MySTestService ms) {
        this.ms = ms;
    }

    public void  run() {
        try {
            synchronized(this){
                while (ticket > 0){
                    Thread.sleep(1000);
                    System.out.println("ticket: " + ticket);
                    ticket--;
                }
            }

            if(ms != null){
                //ms.synPrintNum(5, Thread.currentThread().getName());
                ms.synBlockPrintNum(5, Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyRunnableSynBlock mr = new MyRunnableSynBlock();
        mr.setMs(new MySTestService());
        Thread t = new Thread(mr);
        Thread t1 = new Thread(mr);
        Thread t2 = new Thread(mr);
        t.start();
        t1.start();
        t2.start();
    }
}
