package com.nie.cxf.server;

import com.nie.cxf.model.User;

import javax.jws.WebService;

/**
 * Created by windwant on 2016/1/7.
 */
@WebService
public interface CXFDemo {
    public String sayHello(String word);

    public User getRandomUser();
}
