package org.windwant.designpattern.relations.classstate.visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by windwant on 2016/9/23.
 */
public class NodeContainer {
    private List<ELENode> nodes;

    public NodeContainer() {
        nodes = new ArrayList<ELENode>();
    }

    public void addNode(ELENode eleNode){
        nodes.add(eleNode);
    }

    public void removeNode(ELENode eleNode){
        if(nodes.contains(eleNode)) {
            nodes.remove(eleNode);
        }
    }

    public void execute(Visitor visitor){
        for(ELENode node: nodes){
            node.accept(visitor);
        }
    }
}
