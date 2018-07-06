package org.windwant.concurrent.thread.deadlock;

/**
 * Created by windwant on 2016/6/3.
 */
public class MyTestDeadLockAnother {
    public void run() {
        MyThread mt = new MyThread();
        new Thread(mt, "zhangsan").start();
        new Thread(mt, "lisi").start();
    }

    class MyThread implements Runnable {
        private Object o1 = new Object();

        private boolean flag = true;

        public void run() {
            if(flag) {
                flag = !flag;
                lockmethod();
            }else{
                flag = !flag;
                lockmethod();
            }
        }

        public synchronized void lockmethod(){
            synchronized (o1) {
                System.out.println(Thread.currentThread().getName() + " have o1");
                System.out.println("o1 lock:" + Thread.currentThread().holdsLock(o1));
                System.out.println("flag lock:" + Thread.currentThread().holdsLock(flag));
                try {
                    o1.wait(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new MyTestDeadLockAnother().run();
    }
}
