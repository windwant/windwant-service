package org.windwant.designpattern.relations.classes.command;

/**
 * Created by aayongche on 2016/9/22.
 */
public abstract class Command {
    protected Group group;

    public Command(Group group) {
        this.group = group;
    }

    public abstract void execute();
}
