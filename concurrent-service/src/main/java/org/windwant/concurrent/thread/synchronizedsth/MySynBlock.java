package org.windwant.concurrent.thread.synchronizedsth;

/**
 * Created by windwant on 2016/5/23.
 */
public class MySynBlock implements Runnable{
    private int ticket = 5;
    private MyService myService;

    public void setMs(MyService myService) {
        this.myService = myService;
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

            if(myService != null){
                //ms.synPrintNum(5, Thread.currentThread().getName());
                myService.synBlockPrintNum(5, Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MySynBlock mr = new MySynBlock();
        mr.setMs(new MyService());
        Thread t = new Thread(mr);
        Thread t1 = new Thread(mr);
        Thread t2 = new Thread(mr);
        t.start();
        t1.start();
        t2.start();
    }
}
