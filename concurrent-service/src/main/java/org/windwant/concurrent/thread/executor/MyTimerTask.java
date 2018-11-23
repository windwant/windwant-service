package org.windwant.concurrent.thread.executor;
import java.util.TimerTask;

public class MyTimerTask extends TimerTask{

    @Override
    public void run() {
        System.out.println("timer task");
    }
}
