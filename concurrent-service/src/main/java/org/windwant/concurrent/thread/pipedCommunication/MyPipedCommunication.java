package org.windwant.concurrent.thread.pipedCommunication;


import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * 线程间管道通信
 * Created by windwant on 2017/2/10.
 */
public class MyPipedCommunication {

    public static void main(String[] args) {
        Producer p = new Producer();
        Consumer c = new Consumer();
        PipedOutputStream pp = p.getOut();
        PipedInputStream ppi = c.getIn();
        try {
            pp.connect(ppi);
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.start();
        c.start();
    }
}

