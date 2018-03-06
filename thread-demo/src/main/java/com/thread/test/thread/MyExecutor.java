package com.thread.test.thread;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * schedule(Runnable command, long delay, TimeUnit unit)
 * @ command: 需要执行的任务
 * @ delay：任务执行需要延迟的时间
 * @ unit：时间单位
 *
 * 一次性执行任务，执行完成结束
 *
 * ScheduledExecutorService：
 * scheduleAtFixedRate（Runnable command, long initialDelay, long period, TimeUnit unit）
 * @ Runnable command: 需要执行的任务
 * @ long initialDelay：第一次执行延迟的时间
 * @ long period：间隔周期
 * @ TimeUnit unit
 *
 * 包含首次延迟的周期性执行任务，第一次执行：delay+period，第二次：delay+2*period,以此类推...
 * 停止：异常停止执行，主动调用停止方法
 * 如果某一个周期执行时间超过设定的period，则后续顺延
 *
 * scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit)
 * @ command: 需要执行的任务
 * @ initialDelay：第一次执行延迟的时间
 * @ delay：周期之间的延迟，间隔
 * @ unit：前两个参数的单位
 *
 * 周期性执行任务：第一次执行：initialDelay+delay，第二次：initialDelay+2*delay,以此类推...
 * 停止：异常停止执行，主动调用停止方法
 * 不顺延
 *
 * Created by windwant on 2016/5/26.
 */
public class MyExecutor {
    public static void main(String[] args) {
        testTimer();
    }

    //顺延 单线程 基于系统时间
    public static void testTimer(){
        new Timer().schedule(new MyTimerTask(), 2000, 5000);
    }

    //多线程 基于相对时间
    public static void testExecutors(){
        MyERunnable mer = new MyERunnable(5);
//        ExecutorService es = Executors.newCachedThreadPool();
//        ExecutorService es = Executors.newFixedThreadPool(2);
        ScheduledExecutorService es = Executors.newScheduledThreadPool(2);
        es.schedule(mer, 10000, TimeUnit.SECONDS.MILLISECONDS);
        es.scheduleAtFixedRate(mer, 2, 10, TimeUnit.SECONDS);
        es.scheduleWithFixedDelay(mer, 1, 5, TimeUnit.SECONDS);
        es.shutdown();
    }
}

class MyERunnable implements Runnable{
    private int num = 0;
    MyERunnable(int num){
        this.num = num;
    }
    public void run() {
        ReentrantLock lock = new ReentrantLock();
        try{
            lock.lock();
            for (int i = 0; i < num; i++) {
                System.out.println("current thread: " + Thread.currentThread().getName() + " num--" + i);
                Thread.sleep(1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}

class MyTimerTask extends TimerTask{

    @Override
    public void run() {
        System.out.println("timer task");
    }
}
