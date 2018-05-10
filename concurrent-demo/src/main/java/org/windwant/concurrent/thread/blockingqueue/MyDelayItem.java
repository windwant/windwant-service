package org.windwant.concurrent.thread.blockingqueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 *
 * 延迟队列元素 实现排序
 *
 * Created by Administrator on 18-5-9.
 */
public class MyDelayItem implements Delayed {

    private long liveTime;

    private long removeTime;

    public MyDelayItem(long liveTime, long removeTime){
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