package org.windwant.designpattern.structure.bridge;

/**
 * 电视功能接口
 * Created by aayongche on 2016/9/21.
 */
public interface TV {
    void turnOn();

    void turnOff();

    void switchChannel(int channel);
}
