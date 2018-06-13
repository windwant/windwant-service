package org.windwant.designpattern.structure.flyweight;

/**
 * Created by windwant on 2016/9/21.
 */
public class SharedObject {

    public void startWork(){
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(1000);
                System.out.println("working...");
            }
            PooledSth.releaseConn(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
