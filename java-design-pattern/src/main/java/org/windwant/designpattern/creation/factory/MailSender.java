package org.windwant.designpattern.creation.factory;

/**
 * Created by aayongche on 2016/9/20.
 */
public class MailSender implements Sender {

    public void send() {
        System.out.println("Mail Sender send...");
    }
}
