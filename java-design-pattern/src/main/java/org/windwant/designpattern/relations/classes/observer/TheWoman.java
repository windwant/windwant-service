package org.windwant.designpattern.relations.classes.observer;

import java.util.Observable;

/**
 * Created by aayongche on 2016/9/22.
 */
public class TheWoman extends TheOne {
    public TheWoman(String name){
        super(name);
    }

    public void haveFun(){
        System.out.println("woman have fun...");
        super.setChanged();
        super.notifyObservers("woman begin have fun...");
    }

    public void haveFood(){
        System.out.println("woman have food...");
        super.setChanged();
        super.notifyObservers("woman begin have food...");
    }
}
