package com.thread.test.thread.blockingqueue;

import java.util.concurrent.*;

/**
 * Created by windwant on 2016/5/26.
 */
public class MyBlockingQueue {
    public static void main(String[] args) throws InterruptedException {
        testArrayBlockingQueue();
    }

    /**
     * 公平性 构造函数 true
     */
    public static void testArrayBlockingQueue(){
        BlockingQueue<String> abq = new ArrayBlockingQueue<String>(5);
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new MyPro(abq, 1000));
        es.execute(new MyCus(abq, 5000));
        es.shutdown();
    }

    /**
     * 基于链表节点的可设置容量的队列，先进先出，队尾插入元素，队首获取元素。
     * 链表队列比基于数据的队列有更高的存取效率，但是在并发应用中效率无法预测。
     */
    public static void testLinkedBlockingQueue(){
        BlockingQueue<String> abq = new LinkedBlockingQueue<String>(5);
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new MyPro(abq, 20));
        es.execute(new MyCus(abq, 2000));
        es.shutdown();
    }

    /**
     * DelayQueue
     * 无容量限制的阻塞队列，元素包含延迟时限，只有到达时限，元素才能被取出。
     * 队列顶部是距离到期时间最远的元素。
     * 如果所有的元素都未到期，将会返回null。
     * 元素在执行getDelay()方法返回值小于等于0时过期，即使没有被通过take或者poll执行提取，它们也会被当作一般元素对待。
     * 队列size方法返回所有元素的数量。
     * 队列不能包含null元素。
     */
    public static void testDelayQueue() throws InterruptedException {
        DelayQueue<MyDelayItem> dq = new DelayQueue<MyDelayItem>();
        ExecutorService es = Executors.newFixedThreadPool(5);
        es.execute(new MyDelayPro(dq, 1000));
        es.execute(new MyDelayCus(dq, 10000));
        es.shutdown();
    }

    /**
     * 无容量限制的阻塞队列，元素顺序维持策略同PriorityQueue一样，支持阻塞获取
     * 不允许添加null元素
     * 元素必须支持排序
     * 支持集合遍历，排序
     */
    public static void testPriorityBlockingQueue() throws InterruptedException {
        PriorityBlockingQueue<MyPriorityItem> pbq = new PriorityBlockingQueue<MyPriorityItem>();
        ExecutorService es = Executors.newFixedThreadPool(5);
        es.execute(new MyPriorityPro(pbq, 1000));
        es.execute(new MyPriorityCus(pbq, 10000));
        es.shutdown();
    }

    /**
     * 阻塞队列，插入元素和提取元素必须同步。
     * 异步队列没有容量的概念。
     * 无法使用peek，因为只有当你尝试移除时，元素才会存在。
     * 无法插入元素，除非有另外一个线程同时尝试获取元素。
     * 不支持遍历操作，因为队列中根本没有元素。
     * 队列的顶部就是尝试插入元素的线程要插入的元素。
     * 如果没有尝试插入元素的线程，那么就不存在能够提取的元素，poll会返回null。
     * 集合操作contains返null
     * 不允许插入null元素
     * */
    public static void testSynchronousQueue() throws InterruptedException {
        SynchronousQueue<String> sq = new SynchronousQueue<String>();
        ExecutorService es = Executors.newFixedThreadPool(5);
        es.execute(new MySynchronousPro(sq, 1000));
        es.execute(new MySynchronousCus(sq, 2000));
        es.shutdown();
    }
}

