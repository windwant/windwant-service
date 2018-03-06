package org.windwant.designpattern.creation.factory.simplefactory;

import org.windwant.designpattern.creation.factory.MailSender;
import org.windwant.designpattern.creation.factory.SMSSender;
import org.windwant.designpattern.creation.factory.Sender;

/**
 * Created by aayongche on 2016/9/20.
 */
public class MultiMethodFactory {

    public Sender produceMailSender(){
        return new MailSender();
    }

    public Sender produceSMSSender(){
        return new SMSSender();
    }
}
