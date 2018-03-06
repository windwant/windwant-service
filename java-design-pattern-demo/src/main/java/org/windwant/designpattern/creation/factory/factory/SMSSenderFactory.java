package org.windwant.designpattern.creation.factory.factory;

import org.windwant.designpattern.creation.factory.SMSSender;
import org.windwant.designpattern.creation.factory.Sender;

/**
 * Created by aayongche on 2016/9/20.
 */
public class SMSSenderFactory implements Factory {

    public Sender produce() {
        return new SMSSender();
    }
}
