package org.windwant.designpattern.structure.proxy;

/**
 * Created by windwant on 2016/9/21.
 */
public class MovieStarProxy implements Star {
    private Star star;

    public MovieStarProxy(Star star){
        this.star = star;
    }

    /* ����movie show condition */
    public void movieShow(int money) {
        if (money > 10000) {
            star.movieShow(money);
        } else {
            System.out.println("Movie star does not take movie show witch noney less than 10000");
        }
    }

    /* ����tv show condition */
    public void tvShow(int money) {
        if(money > 10000){
            star.tvShow(money);
        }else {
            System.out.println("Movie star does not take tv show witch noney less than 20000");
        }
    }
}
