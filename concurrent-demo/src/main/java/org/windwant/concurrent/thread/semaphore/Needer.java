package org.windwant.concurrent.thread.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Needer implements Runnable{

    private Semaphore sp;

    private int seconds;

    private String neederName;

    public Needer(Semaphore sp, int seconds, String neederName){
        this.sp = sp;
        this.seconds = seconds;
        this.neederName = neederName;
    }

    public void run() {
        try{
            sp.acquire();
            System.out.println("needer " + neederName + " begin, need time: " + seconds + "s");
            long b = System.currentTimeMillis();
            for (int i = 0; i < seconds; i++) {
                Thread.sleep(1000);
                System.out.println("needer: " + neederName + "============" + i + "============");
            }
            long d = System.currentTimeMillis() - b;
            System.out.println("needer " + neederName + " over, executing time: " + TimeUnit.SECONDS.convert(d, TimeUnit.MILLISECONDS));
            sp.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

