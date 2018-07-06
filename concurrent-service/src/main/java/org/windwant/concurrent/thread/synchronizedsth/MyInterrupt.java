package org.windwant.concurrent.thread.synchronizedsth;

/**
 * Created by windwant on 2016/5/23.
 */
public class MyInterrupt implements Runnable{
    private int ticket = 5;
    private MyService myService;

    public void setMyService(MyService myService) {
        this.myService = myService;
    }

    public void  run() {
        try {
            if(myService != null){
                myService.lockHasInterrupt();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyInterrupt myInterrupt = new MyInterrupt();
        myInterrupt.setMyService(new MyService());
        MySynMethod mySynMethod = new MySynMethod();
        final Thread t = new Thread(myInterrupt);
        final Thread t1 = new Thread(mySynMethod);
        t1.start();
        t.start();
        new Thread(() -> {
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
        }).start();
    }
}
