package com.thread.test.thread;


import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 线程间管道通信
 * Created by windwant on 2017/2/10.
 */
public class MyThreadPipCommunication {

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

class Consumer extends Thread {

    @Override
    public void run() {
        consume();
    }

    public PipedInputStream getIn() {
        return in;
    }

    PipedInputStream in = new PipedInputStream();;

    private void consume() {
        byte[] bys = new byte[1024];
        try {
            while (true) {
                in.read(bys);
                System.out.println(new String(bys));
                bys = new byte[1024];
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
}

class Producer extends Thread {

    @Override
    public void run() {
        produce();
    }

    PipedOutputStream out = new PipedOutputStream();;

    private void produce() {
        int i = 0;
        while (true) {
            i++;
            try {
                int s = ThreadLocalRandom.current().nextInt(5)*1000;
                Thread.sleep(s);
                out.write(("after: " + s/1000 + "s hello" + i + "\r\n").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public PipedOutputStream getOut() {
        return out;
    }
}
