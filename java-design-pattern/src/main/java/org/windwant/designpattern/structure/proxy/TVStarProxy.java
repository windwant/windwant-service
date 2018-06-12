package org.windwant.designpattern.structure.proxy;

/**
 * Created by aayongche on 2016/9/21.
 */
public class TVStarProxy implements Star {
    private Star star;

    public TVStarProxy(Star star){
        this.star = star;
    }

    /* 代理movie show condition */
    public void movieShow(int money) {
        if (money > 20000) {
            star.movieShow(money);
        } else {
            System.out.println("TV star does not take movie show witch noney less than 10000");
        }
    }

    /* 代理TV show condition */
    public void tvShow(int money) {
        if(money > 10000){
            star.tvShow(money);
        }else {
            System.out.println("TV star does not take tv show witch noney less than 20000");
        }
    }
}
