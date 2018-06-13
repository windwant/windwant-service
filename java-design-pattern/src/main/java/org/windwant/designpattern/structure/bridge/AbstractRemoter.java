package org.windwant.designpattern.structure.bridge;

/**
 * ң����������
 * Created by windwant on 2016/9/21.
 */
public abstract class AbstractRemoter {
    private TV tv;

    public AbstractRemoter(TV tv){
        this.tv = tv;
    }

    public void turnOn() {
        tv.turnOn();
    }

    public void turnOff() {
        tv.turnOff();
    }

    public void switchChannel(int channel) {
        tv.switchChannel(channel);
    }
}
