package org.windwant.designpattern.relations.classes.chain;

/**
 * Created by windwant on 2016/9/22.
 */
public class RealWoman implements Woman{

    int type;

    String request;

    public RealWoman() {
    }

    public RealWoman(int type, String request) {
        this.type = type;
        this.request = request;
    }

    public String getRequest() {
        return request;
    }

    public int getType() {
        return type;
    }
}
