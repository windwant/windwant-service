package org.windwant.designpattern.relations.classstate.mediator;

/**
 * Created by aayongche on 2016/9/21.
 */
public abstract class AbstractColleage {
    protected AbstractMediator mediator;

    public AbstractColleage(AbstractMediator mediator){
        this.mediator = mediator;
    }
}
