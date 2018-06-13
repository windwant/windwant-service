package org.windwant.designpattern.creation.factory.simplefactory;

import org.windwant.designpattern.creation.factory.MailSender;
import org.windwant.designpattern.creation.factory.SMSSender;
import org.windwant.designpattern.creation.factory.Sender;

/**
 * Created by windwant on 2016/9/20.
 */
public class MultiStaticMethodFactory {

    public static Sender produceMailSender(){
        return new MailSender();
    }

    public static Sender produceSMSSender(){
        return new SMSSender();
    }
}
