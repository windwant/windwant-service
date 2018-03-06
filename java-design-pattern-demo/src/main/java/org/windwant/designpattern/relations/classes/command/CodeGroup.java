package org.windwant.designpattern.relations.classes.command;

/**
 * Created by aayongche on 2016/9/22.
 */
public class CodeGroup extends Group {

    @Override
    public void findGroup() {
        System.out.println("find code group...");
    }

    @Override
    public void add() {
        System.out.println("code group do customer's requiring new functions...");
    }

    @Override
    public void delete() {
        System.out.println("code group do customer's requiring remove functions...");
    }

    @Override
    public void change() {
        System.out.println("code group do customer's requiring change functions...");
    }

    @Override
    public void plan() {
        System.out.println("code group do customer's requiring change functions plan...");
    }
}
