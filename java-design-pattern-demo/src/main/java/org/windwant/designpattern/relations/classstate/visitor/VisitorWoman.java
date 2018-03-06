package org.windwant.designpattern.relations.classstate.visitor;

/**
 * Created by aayongche on 2016/9/23.
 */
public class VisitorWoman implements Visitor {

    public void visite(ELENode eleNode) {
        eleNode.execute();
    }
}
