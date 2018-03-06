package org.windwant.spring.web.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by windwant on 2016/6/3.
 */
@SpringBootApplication
public class MyTest {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MyTest.class);
        app.setWebEnvironment(true);

        Set<Object> set = new HashSet<Object>();
        set.add("classpath:applicationContext.xml");
        app.setSources(set);
        app.run(args);
    }
}
