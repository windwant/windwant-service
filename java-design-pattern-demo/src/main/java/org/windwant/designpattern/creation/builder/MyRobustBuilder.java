package org.windwant.designpattern.creation.builder;

/**
 * Created by aayongche on 2016/9/21.
 */
public class MyRobustBuilder implements RobustBuilder {

    private Robust robust;

    public void init() {
        robust = new Robust();
    }

    public void buildHead(String head) {
        robust.setHead(head);
    }

    public void buildBody(String body) {
        robust.setBody(body);
    }

    public Robust getRobust() {
        return robust;
    }
}
