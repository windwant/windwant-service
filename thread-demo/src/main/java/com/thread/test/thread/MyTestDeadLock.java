package com.thread.test.thread;

/**
 * Created by windwant on 2016/6/3.
 */
public class MyTestDeadLock {
    public void run() {
        MyThread mt = new MyThread();
        new Thread(mt, "zhangsan").start();
        new Thread(mt, "lisi").start();
    }

    class MyThread implements Runnable {
        private Object o1 = new Object();
        private Object o2 = new Object();
        private boolean flag = true;


        public void run() {
            if (flag) {
                flag = false;
                synchronized (o1) {
                    System.out.println(Thread.currentThread().getName() + " have o1");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o2) {
                        System.out.println(Thread.currentThread().getName() + " have o2");
                    }
                }
            } else {
                flag = true;
                synchronized (o2) {
                    System.out.println(Thread.currentThread().getName() + " have o2");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o1) {
                        System.out.println(Thread.currentThread().getName() + " have o1");
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new MyTestDeadLock().run();
    }
}
