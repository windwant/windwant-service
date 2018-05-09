package com.thread.test.thread.blockingqueue;

/**
 * 延迟队列元素 时限排序对比延迟队列
 *
 * Created by Administrator on 18-5-9.
 */
public class MyPriorityItem implements Comparable<MyPriorityItem> {

    private int priority;

    public MyPriorityItem(int priority){
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