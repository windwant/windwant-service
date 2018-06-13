package org.windwant.designpattern.relations.classstate.memento;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by windwant on 2016/9/23.
 */
public class Storage {
    private List<Memento> mementos;

    public Storage() {
        this.mementos = new ArrayList<Memento>();
    }

    public void saveMemento(Memento memento){
        mementos.add(memento);
    }

    public Memento getSavePoint(int index){
        if(index > mementos.size() - 1){
            return null;
        }else {
            return mementos.get(index);
        }
    }
}
