package org.windwant.designpattern.relations.classes.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by windwant on 2016/9/22.
 */
public class TheFirstWatcher implements Observer {

    public void update(Observable o, Object arg) {
        System.out.println("the first watcher get new man situation...");
        report(((TheOne) o).getName() + ": " + arg.toString());
    }

    private void report(String context){
        System.out.println("the first watcher report message: " + context);
    }
}
