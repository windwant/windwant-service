package com.thread.test.thread;

/**
 * Created by windwant on 2016/5/23.
 */
public class MyRunnableInterrupt implements Runnable{
    private int ticket = 5;
    private MySTestService ms;

    public void setMs(MySTestService ms) {
        this.ms = ms;
    }

    public void  run() {
        try {
            if(ms != null){
                ms.lockHasInterrupt();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyRunnableInterrupt mr = new MyRunnableInterrupt();
        mr.setMs(new MySTestService());
        MyRunnableSyn mr1 = new MyRunnableSyn();
        final Thread t = new Thread(mr);
        final Thread t1 = new Thread(mr1);
        t1.start();
        t.start();
        new Thread(new Runnable() {

            public void run() {
                long start = System.currentTimeMillis();
                for (;;) {
                    //等5秒钟去中断读
                    if (System.currentTimeMillis()
                            - start > 5000) {
                        System.out.println("不等了，尝试中断");
                        t.interrupt();  //尝试中断读线程
                        break;
                    }

                }

                System.out.println(t.isInterrupted());
            }
        }).start();
    }
}
