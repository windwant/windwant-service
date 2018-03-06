package org.windwant.spring.web.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by windwant on 2017/4/27.
 */
public class AopServer {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:*.xml");

        Performer performer = (Performer)ctx.getBean("per");
        performer.count((int) (Math.random() * 10));
        performer.perform("piano");
        performer.myOwn();
        //performer.slient();
        ((PerformerSmile)performer).smile();
        ((PerformerCallHelp)performer).callHelp();
    }
}
