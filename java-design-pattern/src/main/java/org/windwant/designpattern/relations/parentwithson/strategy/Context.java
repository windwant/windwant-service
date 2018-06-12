package org.windwant.designpattern.relations.parentwithson.strategy;

/**
 * Created by aayongche on 2016/9/21.
 */
public class Context {

    private Strategy stategy;

    public Context(Strategy stategy){
        this.stategy = stategy;
    }

    public void operate(){
        stategy.operate();
    }

}
