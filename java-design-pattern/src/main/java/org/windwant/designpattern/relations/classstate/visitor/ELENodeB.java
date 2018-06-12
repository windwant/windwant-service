package org.windwant.designpattern.relations.classstate.visitor;

/**
 * Created by aayongche on 2016/9/23.
 */
public class ELENodeB implements ELENode {

    public void accept(Visitor visitor) {
        visitor.visite(this);
    }

    public void execute() {
        System.out.println("ELENodeB accept visite, do sth...");
    }
}
