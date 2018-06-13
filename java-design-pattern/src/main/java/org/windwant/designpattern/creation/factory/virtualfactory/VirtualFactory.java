package org.windwant.designpattern.creation.factory.virtualfactory;

import org.windwant.designpattern.creation.factory.Sender;

/**
 * Created by windwant on 2016/9/21.
 */
public interface VirtualFactory {
    /* Sender��Ʒ��  */
    Sender produceSender();

    /* Receiver  */
    Receiver produceReceiver();

}
