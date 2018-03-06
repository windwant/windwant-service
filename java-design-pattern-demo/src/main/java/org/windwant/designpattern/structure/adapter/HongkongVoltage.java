package org.windwant.designpattern.structure.adapter;

/**
 * Created by aayongche on 2016/9/21.
 */
public class HongkongVoltage implements PowerVoltage {

    public void givePower() {
        System.out.println("hongkong voltage 250V...");
    }
}
