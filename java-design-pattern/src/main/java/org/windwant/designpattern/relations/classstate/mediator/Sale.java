package org.windwant.designpattern.relations.classstate.mediator;

import java.util.Random;

/**
 * Created by windwant on 2016/9/21.
 */
public class Sale extends AbstractColleage{

    public Sale(AbstractMediator mediator) {
        super(mediator);
    }

    public void sellComputer(int num){
        System.out.println("sale computer num: " + num);
        super.mediator.execute("sale.sell", num);
    }

    public int getSaleStatus(){
        Random rand = new Random(System.currentTimeMillis());
        int saleStatus = rand.nextInt(100);
        System.out.println("computer sell num: " + saleStatus);
        return saleStatus;
    }

    public void offSale(){
        System.out.println("off sale...");
        super.mediator.execute("sale.offsell");
    }
}
