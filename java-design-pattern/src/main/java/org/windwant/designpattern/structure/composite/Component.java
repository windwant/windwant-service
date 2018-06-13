package org.windwant.designpattern.structure.composite;

/**
 * Created by windwant on 2016/9/21.
 */
public abstract class Component {
    protected String name;

    public Component(String name){
        super();
        this.name = name;
    }

    public abstract void add(Component component);
    public abstract void remove(Component component);
    public abstract void display(int depth);
}
