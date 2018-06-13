package org.windwant.designpattern.structure.decorator;

/**
 * Created by windwant on 2016/9/21.
 */
public class TextComponent implements Component {

    public void operate() {
        System.out.println(" -----------------------------");
        System.out.println("|                             |");
        System.out.println("|      text component...      |");
        System.out.println("|                             |");
        System.out.println(" -----------------------------");
    }
}
