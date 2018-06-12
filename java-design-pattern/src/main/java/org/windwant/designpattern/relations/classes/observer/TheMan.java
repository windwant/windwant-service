package org.windwant.designpattern.relations.classes.observer;

import java.util.Observable;

/**
 * Created by aayongche on 2016/9/22.
 */
public class TheMan extends TheOne {
    public TheMan(String name){
        super(name);
    }

    public void haveFun(){
        System.out.println("man have fun...");
        super.setChanged();
        super.notifyObservers("man begin have fun...");
    }

    public void haveFood(){
        System.out.println("man have food...");
        super.setChanged();
        super.notifyObservers("man begin have food...");
    }
}
