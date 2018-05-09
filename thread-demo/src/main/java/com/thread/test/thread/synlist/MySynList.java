package com.thread.test.thread.synlist;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by windwant on 2016/12/1.
 */
public class MySynList {

    public static void main(String[] args) throws Exception {
        SynList list = new SynList();
        Thread c = new Thread(new MyConsumer(list));
        Thread p = new Thread(new MyProducer(list));
        c.start();
        p.start();
    }

    static class MyConsumer implements Runnable {
        private SynList list;
        public MyConsumer(SynList list){
            this.list = list;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list.get();
            }
        }
    }

    static class MyProducer implements Runnable {
        private SynList list;
        public MyProducer(SynList list){
            this.list = list;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list.put(ThreadLocalRandom.current().nextInt(100));
            }
        }
    }

    static class SynList{
        private final ReentrantLock lock = new ReentrantLock();
        private final Condition has = lock.newCondition();
        private List list = new ArrayList<Integer>();

        public void put(Integer ele){
            final ReentrantLock lock = this.lock;
            try{
                lock.lock();
                list.add(ele);
                System.out.println("P: put ele to list: " + ele);
                has.signal();
            }finally {
                lock.unlock();
            }
        }

        public void get(){
            final ReentrantLock lock = this.lock;
            try{
                lock.lock();
                if(list.size() > 0){
                    Integer i = (Integer) list.get(0);
                    list.remove(0);
                    System.out.println("T: take ele from list: " + i);
                }else{
                    System.out.println("T: await ele to take ...");
                    has.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

    }

    public static void privilegedCallable(){
        Integer result = 0;
        Callable callable = Executors.privilegedCallable(() -> 100);
        try {
            System.out.println(callable.call());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void task() throws ExecutionException, InterruptedException {
        FutureTask futureTask = new FutureTask(() -> {
            Thread.sleep(5000);
            return ThreadLocalRandom.current().nextInt(9999);
        });
        new Thread(futureTask).start();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if(futureTask.isDone()){
                System.out.println("over: " + futureTask.get());
                break;
            }
            System.out.println("cycle: " + i);
        }
    }
}
