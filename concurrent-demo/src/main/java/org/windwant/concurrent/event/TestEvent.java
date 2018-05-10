package org.windwant.concurrent.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by windwant on 2016/5/20.
 */
public class TestEvent extends ApplicationEvent {

    public TestEvent(Object source) {
        super(source);
    }

    public String msg;
    public TestEvent(Object source, String msg){
        super(source);
        this.msg = msg;
    }

    public void print(){
        System.out.println(msg);
    }
}
