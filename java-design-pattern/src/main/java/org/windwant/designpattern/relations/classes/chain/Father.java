package org.windwant.designpattern.relations.classes.chain;

/**
 * Created by windwant on 2016/9/22.
 */
public class Father extends Handler {
    public Father() {
        super(1);
    }

    @Override
    public void response(Woman woman) {
        System.out.println("Father get request from daughter...");
        System.out.println("request: " + woman.getRequest());
        System.out.println("Father response, aggree...");
    }
}
