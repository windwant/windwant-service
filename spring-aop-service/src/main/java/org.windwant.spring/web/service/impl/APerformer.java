package org.windwant.spring.web.service.impl;

import org.springframework.stereotype.Component;
import org.windwant.spring.core.Anotation.MyTypeAnotation;
import org.windwant.spring.web.service.Performer;

/**
 * Created by windwant on 2016/2/24.
 */
@Component("per")
public class APerformer implements Performer {
    public String perform(String strument) {
        System.out.println("APerformer: perform " + strument);
        int i = (int)System.currentTimeMillis()%2;
        if(i == 0){
            return "APerformer feel good !";
        }else{
            return "APerformer feel not good !";
        }
    }

    public void count(int number) {
        System.out.println("APerformer: count " + number);
    }

    public void slient(){
        System.out.println("APerformer: need sometime to step into slient !");
        int a = 10/0;
    }

    @MyTypeAnotation(value = "org.windwant.spring anotation", type = 1)
    public void myOwn() {
        System.out.println("Test My Anotation");
    }
}
