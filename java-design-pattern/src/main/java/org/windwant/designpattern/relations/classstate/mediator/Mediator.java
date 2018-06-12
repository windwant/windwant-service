package org.windwant.designpattern.relations.classstate.mediator;

/**
 * Created by aayongche on 2016/9/21.
 */
public class Mediator extends AbstractMediator {


    @Override
    public void execute(String str, Object... objects) {
        if(str.equals("purchase.buy")){ //采购电脑
            this.buyComputer((Integer)objects[0]);
        }else if(str.equals("sale.sell")){ //销售电脑
            this.sellComputer((Integer)objects[0]);
        }else if(str.equals("sale.offsell")){ //折价销售
            this.offSell();
        }else if(str.equals("stock.clear")){ //清仓处理
            this.clearStock();
        }
    }

    private void buyComputer(int num){
        int saleStatus = super.sale.getSaleStatus();
        int buyNum = num;
        if(saleStatus > 80){
            System.out.println("purchase computer num: " + num);
        } else {
            buyNum = num / 2;
            System.out.println("purchase computer num: " + buyNum);
        }
        super.stock.increase(buyNum);
    }

    private void sellComputer(int num){
        if(super.stock.getStockNum() < num){
            super.purchase.buyComputer(num);
        }
        super.stock.decrease(num);
    }

    private void offSell(){
        System.out.println("offsell num: " + stock.getStockNum());
    }

    private void clearStock(){
        super.sale.offSale();
        super.purchase.refuseBuyComputer();
    }
}