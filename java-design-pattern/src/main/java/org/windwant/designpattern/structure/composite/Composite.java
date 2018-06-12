package org.windwant.designpattern.structure.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aayongche on 2016/9/21.
 */
public class Composite extends Component {

    private List<Component> children;

    public Composite(String name){
        super(name);
        children = new ArrayList<Component>();
    }

    @Override
    public void add(Component component) {
        children.add(component);
    }

    @Override
    public void remove(Component component) {
        if(children.contains(component)){
            children.remove(component);
        }
    }

    @Override
    public void display(int depth) {
        StringBuffer sb = new StringBuffer("-");
        for (int i = 0; i <= depth; i++) {
            sb.append("-");
        }
        System.out.println(sb.toString()+name);
        for (Component com : children) {
            com.display(depth + 2);
        }
    }
}
