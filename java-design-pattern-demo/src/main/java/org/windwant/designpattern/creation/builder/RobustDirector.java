package org.windwant.designpattern.creation.builder;

/**
 * Created by aayongche on 2016/9/21.
 */
public class RobustDirector {

    private RobustBuilder robustBuilder;

    public RobustDirector(RobustBuilder robustBuilder){
        this.robustBuilder = robustBuilder;
    }

    public Robust buildRobust(String head, String body){
        robustBuilder.init();
        robustBuilder.buildHead(head);
        robustBuilder.buildBody(body);
        return robustBuilder.getRobust();
    }
}
