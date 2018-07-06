package org.windwant.spring.web.service;

/**
 * Created by windwant on 2016/2/24.
 */
public interface Performer {
    String perform(String strument);
    void count(int number);
    void slient();

    void myOwn();
}
