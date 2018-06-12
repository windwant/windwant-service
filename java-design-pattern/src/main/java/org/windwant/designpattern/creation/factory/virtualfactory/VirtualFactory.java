package org.windwant.designpattern.creation.factory.virtualfactory;

import org.windwant.designpattern.creation.factory.Sender;

/**
 * Created by aayongche on 2016/9/21.
 */
public interface VirtualFactory {
    /* Sender²úÆ·Ïß  */
    Sender produceSender();

    /* Receiver  */
    Receiver produceReceiver();

}
