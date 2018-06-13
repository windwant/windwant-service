package org.windwant.designpattern.creation.factory;

/**
 * Created by windwant on 2016/9/20.
 */
public class SMSSender implements Sender {

    public void send() {
        System.out.println("SMS Sender send...");
    }
}
