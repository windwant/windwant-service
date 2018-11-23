package org.windwant.concurrent.thread.pipedCommunication;


import java.io.IOException;
import java.io.PipedInputStream;

public class Consumer extends Thread {

    @Override
    public void run() {
        consume();
    }

    public PipedInputStream getIn() {
        return in;
    }

    //consumer 接收
    PipedInputStream in = new PipedInputStream();;

    private void consume() {
        byte[] bys = new byte[1024];
        try {
            while (true) {
                in.read(bys);
                System.out.println("consumer: " + new String(bys));
                bys = new byte[1024];
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
}

