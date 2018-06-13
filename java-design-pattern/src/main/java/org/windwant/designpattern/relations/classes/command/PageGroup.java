package org.windwant.designpattern.relations.classes.command;

/**
 * Created by windwant on 2016/9/22.
 */
public class PageGroup extends Group {

    @Override
    public void findGroup() {
        System.out.println("find page group...");
    }

    @Override
    public void add() {
        System.out.println("page group do customer's requiring new page...");
    }

    @Override
    public void delete() {
        System.out.println("page group do customer's requiring remove page...");
    }

    @Override
    public void change() {
        System.out.println("page group do customer's requiring change page...");
    }

    @Override
    public void plan() {
        System.out.println("page group do customer's requiring change page plan...");
    }
}
