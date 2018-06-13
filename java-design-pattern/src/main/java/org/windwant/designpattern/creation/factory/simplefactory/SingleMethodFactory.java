package org.windwant.designpattern.creation.factory.simplefactory;

import org.windwant.designpattern.creation.factory.MailSender;
import org.windwant.designpattern.creation.factory.SMSSender;
import org.windwant.designpattern.creation.factory.Sender;

/**
 * Created by windwant on 2016/9/20.
 */
public class SingleMethodFactory {

    public Sender produce(String clazzType){
        if("M".equals(clazzType)){
            return new MailSender();
        }else if("S".equals(clazzType)){
            return new SMSSender();
        }else
            return null;
    }
}
