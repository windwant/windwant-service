package org.windwant.designpattern.relations.classstate.mediator;

/**
 * Created by aayongche on 2016/9/21.
 */
public abstract class AbstractMediator {

    protected Purchase purchase;

    protected Sale sale;

    protected Stock stock;

    public AbstractMediator(){
        purchase = new Purchase(this);
        sale = new Sale(this);
        stock = new Stock(this);
    }

    public abstract void execute(String str, Object...objects);
}
