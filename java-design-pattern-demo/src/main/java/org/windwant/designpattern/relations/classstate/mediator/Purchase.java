package org.windwant.designpattern.relations.classstate.mediator;

/**
 * Created by aayongche on 2016/9/21.
 */
public class Purchase extends AbstractColleage {

    public Purchase(AbstractMediator mediator) {
        super(mediator);
    }

    public void buyComputer(int num){
        System.out.println("buy computer num: " + num);
        this.mediator.execute("purchase.buy", num);
    }

    public void refuseBuyComputer(){
        System.out.println("refuse buy computer...");
    }
}
