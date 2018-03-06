package org.windwant.designpattern.structure.composite;

/**
 * Created by aayongche on 2016/9/21.
 */
public class Leaf extends Component {

    public Leaf(String name){
        super(name);
    }

    @Override
    public void add(Component component) {
        System.out.println("Leaf never add...");
    }

    @Override
    public void remove(Component component) {
        System.out.println("Leaf never remove...");
    }

    @Override
    public void display(int depth) {
        StringBuffer sb = new StringBuffer("-");
        for (int i = 0; i <= depth; i++) {
            sb.append("-");
        }
        System.out.println(sb.toString() + name);
    }
}
