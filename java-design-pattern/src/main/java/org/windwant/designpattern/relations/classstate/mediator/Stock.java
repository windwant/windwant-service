package org.windwant.designpattern.relations.classstate.mediator;

/**
 * Created by aayongche on 2016/9/21.
 */
public class Stock extends AbstractColleage {

    public Stock(AbstractMediator mediator) {
        super(mediator);
    }

    private static int COMPUTER_NUM = 100;

    public void increase(int num){
        COMPUTER_NUM += num;
        System.out.println("current inventory num: " + COMPUTER_NUM);
    }

    public void decrease(int num){
        COMPUTER_NUM -= num;
        System.out.println("current inventory num: " + COMPUTER_NUM);
    }

    public int getStockNum(){
        return COMPUTER_NUM;
    }

    public void clearStock(){
        System.out.println("clear stock num: " + COMPUTER_NUM);
        super.mediator.execute("stock.clear");
    }
}
