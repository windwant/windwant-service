package com.thread.test.listener;

import com.thread.test.event.TestEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * Created by windwant on 2016/5/20.
 */
public class TestListener implements ApplicationListener {
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if(applicationEvent instanceof TestEvent){
            TestEvent t = (TestEvent) applicationEvent;
            t.print();
        }
    }
}
