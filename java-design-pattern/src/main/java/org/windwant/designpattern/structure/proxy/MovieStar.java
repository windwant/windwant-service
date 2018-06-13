package org.windwant.designpattern.structure.proxy;

/**
 * Created by windwant on 2016/9/21.
 */
public class MovieStar implements Star {

    public void movieShow(int money) {
        System.out.println("movie star movie show, pay: " + money);
    }

    public void tvShow(int money) {
        System.out.println("movie star tv show, pay: " + money);
    }
}
