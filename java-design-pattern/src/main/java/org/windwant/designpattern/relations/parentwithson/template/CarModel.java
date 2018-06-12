package org.windwant.designpattern.relations.parentwithson.template;

/**
 * Created by aayongche on 2016/9/22.
 */
public abstract class CarModel {
    private boolean alarmFlag;

    protected abstract void start();

    protected abstract void stop();

    protected abstract void alarm();

    protected abstract void engineBoom();

    final public void run(){
        this.start();
        this.engineBoom();
        if(alarmFlag){
            this.alarm();
        }
        this.stop();
    }

    public boolean isAlarmFlag() {
        return alarmFlag;
    }

    public void setAlarmFlag(boolean alarmFlag) {
        this.alarmFlag = alarmFlag;
    }
}
