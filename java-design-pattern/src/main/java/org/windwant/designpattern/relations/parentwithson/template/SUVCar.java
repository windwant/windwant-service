package org.windwant.designpattern.relations.parentwithson.template;

/**
 * Created by aayongche on 2016/9/22.
 */
public class SUVCar extends CarModel {

    @Override
    protected void start() {
        System.out.println("SUV start...");
    }

    @Override
    protected void stop() {
        System.out.println("SUV stop...");
    }

    @Override
    protected void alarm() {
        System.out.println("SUV alarm...");
    }

    @Override
    protected void engineBoom() {
        System.out.println("SUV engineBoom...");
    }
}
