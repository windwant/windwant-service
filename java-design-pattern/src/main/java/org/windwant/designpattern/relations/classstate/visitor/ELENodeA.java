package org.windwant.designpattern.relations.classstate.visitor;

/**
 * Created by windwant on 2016/9/23.
 */
public class ELENodeA implements ELENode {

    public void accept(Visitor visitor) {
        visitor.visite(this);
        System.out.println("");
    }

    public void execute() {
        System.out.println("ELENodeA accept visite, do sth...");
    }
}
