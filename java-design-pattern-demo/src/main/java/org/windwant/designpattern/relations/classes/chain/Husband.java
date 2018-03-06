package org.windwant.designpattern.relations.classes.chain;

/**
 * Created by aayongche on 2016/9/22.
 */
public class Husband extends Handler {

    public Husband() {
        super(2);
    }

    @Override
    public void response(Woman woman) {
        System.out.println("Husband get request from wife...");
        System.out.println("wife request: " + woman.getRequest());
        System.out.println("Husband response, aggree...");
    }
}
