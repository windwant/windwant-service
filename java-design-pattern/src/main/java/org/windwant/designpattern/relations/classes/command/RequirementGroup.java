package org.windwant.designpattern.relations.classes.command;

/**
 * Created by windwant on 2016/9/22.
 */
public class RequirementGroup extends Group {

    @Override
    public void findGroup() {
        System.out.println("find requirement group...");
    }

    @Override
    public void add() {
        System.out.println("requirement group do customer's requiring new requirement...");
    }

    @Override
    public void delete() {
        System.out.println("requirement group do customer's requiring remove requirement...");
    }

    @Override
    public void change() {
        System.out.println("requirement group do customer's requiring change requirement...");
    }

    @Override
    public void plan() {
        System.out.println("requirement group do customer's requiring change requirement plan...");
    }
}
