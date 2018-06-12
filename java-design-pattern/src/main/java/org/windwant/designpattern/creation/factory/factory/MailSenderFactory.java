package org.windwant.designpattern.creation.factory.factory;

import org.windwant.designpattern.creation.factory.MailSender;
import org.windwant.designpattern.creation.factory.Sender;

/**
 * Created by aayongche on 2016/9/20.
 */
public class MailSenderFactory implements Factory {

    public Sender produce() {
        return new MailSender();
    }
}
