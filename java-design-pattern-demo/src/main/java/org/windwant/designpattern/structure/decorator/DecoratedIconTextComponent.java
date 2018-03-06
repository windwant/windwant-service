package org.windwant.designpattern.structure.decorator;

/**
 * Created by aayongche on 2016/9/21.
 */
public class DecoratedIconTextComponent implements Component {

    private Component component;

    public DecoratedIconTextComponent(Component component){
        this.component = component;
    }

    public void operate() {
        System.out.println(" -----------------------------");
        System.out.println("|           icon              |");
        component.operate();
    }
}
