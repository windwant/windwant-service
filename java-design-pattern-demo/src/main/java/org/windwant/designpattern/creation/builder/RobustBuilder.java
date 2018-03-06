package org.windwant.designpattern.creation.builder;

/**
 * Created by aayongche on 2016/9/21.
 */
public interface RobustBuilder {

    void init();

    void buildHead(String head);

    void buildBody(String body);

    Robust getRobust();
}
