package org.windwant.designpattern.structure.proxy;

/**
 * Created by aayongche on 2016/9/21.
 */
public class TVStar implements Star {

    public void movieShow(int money) {
        System.out.println("tv start movie show, pay: " + money);
    }

    public void tvShow(int money) {
        System.out.println("tv start tv show, pay: " + money);
    }
}
