package org.windwant.designpattern.relations.classes.observer;

import java.util.Observable;

/**
 * Created by windwant on 2016/9/22.
 */
public abstract class TheOne extends Observable {
    private String name;

    protected TheOne(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
