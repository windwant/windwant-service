package org.windwant.designpattern.relations.classes.command;

/**
 * Created by aayongche on 2016/9/22.
 */
public class AddRequirementCommand extends Command {

    public AddRequirementCommand() {
        super(new RequirementGroup());
    }

    @Override
    public void execute() {
        super.group.findGroup();
        super.group.add();
        super.group.plan();
    }
}
