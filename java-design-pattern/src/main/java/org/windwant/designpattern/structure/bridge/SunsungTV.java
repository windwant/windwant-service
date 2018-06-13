package org.windwant.designpattern.structure.bridge;

/**
 * Created by windwant on 2016/9/21.
 */
public class SunsungTV implements TV {

    public void turnOn() {
        System.out.println("sunsung tv turn on...");
    }

    public void turnOff() {
        System.out.println("sunsung tv turn off...");
    }

    public void switchChannel(int channel) {
        System.out.println("sunsung tv switch channel: " + channel);
    }
}
