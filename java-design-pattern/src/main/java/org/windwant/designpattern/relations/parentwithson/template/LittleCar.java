package org.windwant.designpattern.relations.parentwithson.template;

/**
 * Created by aayongche on 2016/9/22.
 */
public class LittleCar extends CarModel {

    @Override
    protected void start() {
        System.out.println("LittleCar start...");
    }

    @Override
    protected void stop() {
        System.out.println("LittleCar stop...");
    }

    @Override
    protected void alarm() {
        System.out.println("LittleCar alarm...");
    }

    @Override
    protected void engineBoom() {
        System.out.println("LittleCar engineBoom...");
    }

    @Override
    public void setAlarmFlag(boolean alarmFlag) {
        super.setAlarmFlag(alarmFlag);
    }
}
