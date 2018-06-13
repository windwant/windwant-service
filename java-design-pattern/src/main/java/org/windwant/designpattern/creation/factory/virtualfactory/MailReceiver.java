package org.windwant.designpattern.creation.factory.virtualfactory;

/**
 * Created by windwant on 2016/9/21.
 */
public class MailReceiver implements Receiver {

    public void receive() {
        System.out.println("Mail Receiver receive...");
    }
}
