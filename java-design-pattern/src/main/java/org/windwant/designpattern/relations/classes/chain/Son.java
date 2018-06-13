package org.windwant.designpattern.relations.classes.chain;

/**
 * Created by windwant on 2016/9/22.
 */
public class Son extends Handler {

    public Son() {
        super(3);
    }

    @Override
    public void response(Woman woman) {
        System.out.println("Son get request from mother...");
        System.out.println("mother request: " + woman.getRequest());
        System.out.println("Son response, aggree...");
    }
}
