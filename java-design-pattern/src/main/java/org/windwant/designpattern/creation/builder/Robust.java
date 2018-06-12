package org.windwant.designpattern.creation.builder;

/**
 * Created by aayongche on 2016/9/21.
 */
public class Robust {
    private String head;

    private String body;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Robust{" +
                "head='" + head + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
