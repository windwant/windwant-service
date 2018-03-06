package org.windwant.designpattern.relations.classes.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by aayongche on 2016/9/22.
 */
public class TheSecondWatcher implements Observer {

    public void update(Observable o, Object arg) {
        System.out.println("the second watcher get new man situation...");
        report(((TheOne) o).getName() + ": " + arg.toString());
    }

    private void report(String context){
        System.out.println("the second watcher report message: " + context);
    }
}
