package org.windwant.designpattern.creation.factory.virtualfactory;

import org.windwant.designpattern.creation.factory.MailSender;
import org.windwant.designpattern.creation.factory.Sender;

/**
 * Created by aayongche on 2016/9/21.
 */
public class MailVirtualFactory implements VirtualFactory {

    public Sender produceSender() {
        return new MailSender();
    }

    public Receiver produceReceiver() {
        return new MailReceiver();
    }
}
