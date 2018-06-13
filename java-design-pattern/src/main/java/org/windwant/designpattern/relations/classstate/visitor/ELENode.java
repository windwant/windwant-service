package org.windwant.designpattern.relations.classstate.visitor;

/**
 * Created by windwant on 2016/9/23.
 */
public interface ELENode {
    void accept(Visitor visitor);

    void execute();
}
