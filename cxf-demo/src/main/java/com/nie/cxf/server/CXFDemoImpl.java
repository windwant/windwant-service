package com.nie.cxf.server;

import com.nie.cxf.model.User;
import com.nie.cxf.server.CXFDemo;

import javax.jws.WebService;

/**
 * Created by windwant on 2016/1/7.
 */
@WebService
public class CXFDemoImpl implements CXFDemo {

    public String sayHello(String word) {
        return "hello " + word;
    }

    public User getRandomUser() {
        return new User(123456, "user" + String.valueOf(Math.random()*1000));
    }
}
