package com.thread.test.thread.pipedCommunication;


import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.concurrent.ThreadLocalRandom;

public class Producer extends Thread {

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
                String msg = "after: " + s/1000 + "s hello" + i + "\r\n";
                System.out.println("producer: " + msg);
                out.write(msg.getBytes());
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
