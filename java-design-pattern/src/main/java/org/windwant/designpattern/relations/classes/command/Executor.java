package org.windwant.designpattern.relations.classes.command;

/**
 * Created by windwant on 2016/9/22.
 */
public class Executor {

    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void action(){
        command.execute();
    }
}
