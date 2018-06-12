package org.windwant.designpattern.creation.factory.virtualfactory;

/**
 * Created by aayongche on 2016/9/21.
 */
public class SMSReceiver implements Receiver {
    public void receive() {
        System.out.println("SMS Receiver receive...");
    }
}
