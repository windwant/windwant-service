package org.windwant.designpattern.relations.classes.command;

/**
 * Created by windwant on 2016/9/22.
 */
public class DeletePageCommand extends Command {

    public DeletePageCommand() {
        super(new PageGroup());
    }

    @Override
    public void execute() {
        super.group.findGroup();
        super.group.delete();
        super.group.plan();
    }
}
