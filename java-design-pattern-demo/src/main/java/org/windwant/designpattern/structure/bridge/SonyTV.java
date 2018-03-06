package org.windwant.designpattern.structure.bridge;

/**
 * Created by aayongche on 2016/9/21.
 */
public class SonyTV implements TV {

    public void turnOn() {
        System.out.println("SonyTV tv turn on...");
    }

    public void turnOff() {
        System.out.println("SonyTV tv turn off...");
    }

    public void switchChannel(int channel) {
        System.out.println("SonyTV tv switch channel: " + channel);
    }
}
