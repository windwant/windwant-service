package org.windwant.designpattern.creation.factory.virtualfactory;

import org.windwant.designpattern.creation.factory.SMSSender;
import org.windwant.designpattern.creation.factory.Sender;

/**
 * Created by windwant on 2016/9/21.
 */
public class SMSVirtualFactory implements VirtualFactory {

    public Sender produceSender() {
        return new SMSSender();
    }

    public Receiver produceReceiver() {
        return new SMSReceiver();
    }
}
