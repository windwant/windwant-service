package org.windwant.designpattern.structure.flyweight;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by windwant on 2016/9/21.
 */
public class PooledSth {
    private static CopyOnWriteArrayList<SharedObject> share = new CopyOnWriteArrayList<SharedObject>();
    static {
        for (int i = 0; i < 10; i++) {
            share.add(new SharedObject());
        }
    }

    public static synchronized SharedObject getShareObject(){
        if(share.size() > 0){
            int index = ThreadLocalRandom.current().nextInt(share.size());
            SharedObject get = share.get(index);
            share.remove(index);
            System.out.println("get one object,remain size:" + share.size());
            return get;
        }
        return null;
    }

    public static synchronized void releaseConn(SharedObject shareObject){
        share.add(shareObject);
        System.out.println("release one object,remain size:" + share.size());
    }
}
