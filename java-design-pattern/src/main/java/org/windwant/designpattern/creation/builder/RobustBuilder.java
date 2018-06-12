package org.windwant.designpattern.creation.builder;

/**
 * Created by aayongche on 2016/9/21.
 */
public class RobustBuilder {

    private String head;

    private String body;

    public static RobustBuilder create(){
        return new RobustBuilder();
    }

    RobustBuilder buildHead(String head){
        this.head = head;
        return this;
    }

    RobustBuilder buildBody(String body){
        this.body = body;
        return this;
    }

    Robust build(){
        Robust robust = new Robust();
        robust.setHead(head);
        robust.setBody(body);
        return robust;
    }

    public static void main(String[] args) {
        Robust robust1 = RobustBuilder.create()
                .buildHead("robust1-head")
                .buildBody("robus1-body")
                .build();
        System.out.println(robust1);

        Robust robust2 = RobustBuilder.create()
                .buildHead("robust2-head")
                .buildBody("robus2-body")
                .build();
        System.out.println(robust2);
    }
}
