package com.thread.test.thread;
import java.util.Random;
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
        es.execute(new MyPriorityBlockingQueuePro(pbq, 1000));
        es.execute(new MyPriorityBlockingQueueCus(pbq, 10000));
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
        es.execute(new MySynchronousQueuePro(sq, 1000));
        es.execute(new MySynchronousQueueCus(sq, 2000));
        es.shutdown();
    }
}

/**
 * 测试生产者
 */
class MyPro implements Runnable{

    private BlockingQueue<String> bq;

    private int period = 1000;

    private Random r = new Random();
    MyPro(BlockingQueue bq, int period){
        this.bq = bq;
        this.period = period;
    }

    public void run() {
        try{
            while (true){
                Thread.sleep(period);
                String value = String.valueOf(r.nextInt(100));
                if(bq.offer(value)){ //offer 能够插入就返回true，否则返回false
                    System.out.println("pro make value: " + value + " queue : " + bq.toString());
                    System.out.println("******************************************************");
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

/**
 * 测试消费者
 */
class MyCus implements Runnable{

    private BlockingQueue<String> bq;

    private int period = 1000;

    private Random r = new Random();
    MyCus(BlockingQueue bq, int period){
        this.bq = bq;
        this.period = period;
    }

    public void run() {
        try{
            while (true){
                Thread.sleep(period);
                String value = bq.take(); //获取队列头部元素，无元素则阻塞
                System.out.println("cus take value: " + value + " queue : " + bq.toString());
                System.out.println("======================================================");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

/**
 * 延迟队列元素 实现排序
 */
class MyDelayItem implements Delayed{

    private long liveTime;

    private long removeTime;

    MyDelayItem(long liveTime, long removeTime){
        this.liveTime = liveTime;
        this.removeTime = TimeUnit.MILLISECONDS.convert(liveTime, TimeUnit.MILLISECONDS) + System.nanoTime();
    }

    public long getDelay(TimeUnit unit) {
        return unit.convert(removeTime - System.nanoTime(), unit);
    }

    public int compareTo(Delayed o) {
        if(o == null) return -1;
        if(o == this) return 0;
        if(o instanceof MyDelayItem){
            MyDelayItem tmp = (MyDelayItem) o;
            if(liveTime > tmp.liveTime){
                return 1;
            }else if(liveTime == tmp.liveTime){
                return 0;
            }else{
                return -1;
            }
        }
        long diff = getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS);
        return diff > 0 ? 1 : diff == 0 ? 0 : -1;
    }

    public String toString(){
        return "{livetime: " + String.valueOf(liveTime) + ", removetime: " + String.valueOf(removeTime) + "}";
    }
}

/**
 * 延迟队列测试生产者
 */
class MyDelayPro implements Runnable{

    private DelayQueue<MyDelayItem> dq;

    private int period = 1000;

    private Random r = new Random();

    MyDelayPro(DelayQueue dq, int period){
        this.dq = dq;
        this.period = period;
    }
    public void run() {
        try{
            while (true){
                Thread.sleep(period);
                if(dq.size() > 5){
                    continue;
                }
                MyDelayItem di = new MyDelayItem(r.nextInt(10), r.nextInt(10));
                dq.offer(di);
                System.out.println("delayqueue: add---" + di.toString() + "size: " + dq.size());
                System.out.println("*************************************");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

/**
 * 延迟队列测试消费者
 */
class MyDelayCus implements Runnable{

    private DelayQueue<MyDelayItem> dq;

    private int period = 1000;

    MyDelayCus(DelayQueue dq, int period){
        this.dq = dq;
        this.period = period;
    }
    public void run() {
        try{
            while (true){
                Thread.sleep(period);
                MyDelayItem di = dq.take();
                System.out.println("delayqueue: remove---" + di.toString());
                System.out.println("delayqueue: ---" + dq.toString());
                System.out.println("======================================");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

/**
 * 延迟队列元素 时限排序对比延迟队列
 */
class MyPriorityItem implements Comparable<MyPriorityItem> {

    private int priority;

    MyPriorityItem(int priority){
        this.priority = priority;
    }

    /**
     * 数字大优先级高
     * @param o
     * @return
     */
    public int compareTo(MyPriorityItem o) {
        if(o == null) return -1;
        if(o == this) return 0;
        if(priority > o.priority){
            return -1;
        }else if(priority == o.priority){
            return 0;
        }else{
            return 1;
        }
    }

    public String toString(){
        return "{priority: " + String.valueOf(priority) + "}";
    }
}

/**
 * 优先队列测试生产者
 */
class MyPriorityBlockingQueuePro implements Runnable{

    private PriorityBlockingQueue<MyPriorityItem> pbq;

    private int period = 1000;

    private Random r = new Random();

    MyPriorityBlockingQueuePro(PriorityBlockingQueue pbq, int period){
        this.pbq = pbq;
        this.period = period;
    }
    public void run() {
        try{
            while (true){
                Thread.sleep(period);
                if(pbq.size() > 5){
                    continue;
                }
                MyPriorityItem pi = new MyPriorityItem(r.nextInt(10));
                pbq.offer(pi);
                System.out.println("PriorityBlockingQueue: add---" + pi.toString() + " size: " + pbq.size());
                System.out.println("PriorityBlockingQueue: " + pbq.toString());
                System.out.println("*************************************");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

/**
 * 优先队列测试消费者
 */
class MyPriorityBlockingQueueCus implements Runnable{

    private PriorityBlockingQueue<MyPriorityItem> pbq;

    private int period = 1000;

    private Random r = new Random();

    MyPriorityBlockingQueueCus(PriorityBlockingQueue pbq, int period){
        this.pbq = pbq;
        this.period = period;
    }
    public void run() {
        try{
            while (true){
                Thread.sleep(period);
                MyPriorityItem di = pbq.take();
                System.out.println("PriorityBlockingQueue: remove---" + di.toString());
                System.out.println("PriorityBlockingQueue: ---" + pbq.toString());
                System.out.println("======================================");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

/**
 * 阻塞队列测试生产者
 */
class MySynchronousQueuePro implements Runnable{

    private SynchronousQueue<String> sq;

    private int period = 1000;

    private Random r = new Random();
    MySynchronousQueuePro(SynchronousQueue sq, int period){
        this.sq = sq;
        this.period = period;
    }

    public void run() {
        try{
            while (true){
                Thread.sleep(period);
                String value = String.valueOf(r.nextInt(100));
                if(sq.offer(value)) {
                    System.out.println("pro make value: " + value + " synchronous :" + sq.toString());
                    System.out.println("******************************************************");
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

/**
 * 阻塞队列测试消费者
 */
class MySynchronousQueueCus implements Runnable{

    private BlockingQueue<String> sq;

    private int period = 1000;

    MySynchronousQueueCus(BlockingQueue sq, int period){
        this.sq = sq;
        this.period = period;
    }

    public void run() {
        try{
            while (true){
                Thread.sleep(period);
                String value = sq.take();
                System.out.println("cus take value: " + value + " synchronous :" + sq.toString());
                System.out.println("======================================================");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
