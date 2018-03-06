package org.windwant.designpattern.structure.bridge;

/**
 * Created by aayongche on 2016/9/21.
 */
public class BridgeRemoterOfSunsung extends AbstractRemoter {

    public BridgeRemoterOfSunsung(TV tv) {
        super(tv);
    }

    @Override
    public void turnOn() {
        super.turnOn();
    }

    @Override
    public void turnOff() {
        super.turnOff();
    }

    @Override
    public void switchChannel(int channel) {
        super.switchChannel(channel);
    }
}
