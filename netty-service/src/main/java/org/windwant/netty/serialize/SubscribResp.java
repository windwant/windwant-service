package org.windwant.netty.serialize;

import java.io.Serializable;

/**
 * 回复对象
 * SubscribResp
 */
public class SubscribResp implements Serializable {

    private static final Long serialVersionUID = 1L;

    private int subReqID;

    private int respCode;

    private String desc;

    public int getSubReqID() {
        return subReqID;
    }

    public void setSubReqID(int subReqID) {
        this.subReqID = subReqID;
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String toString(){
        return "SubscribResp [subReqID: " + subReqID
                + ", respCode: " + respCode
                + ", desc: " + desc
                + "]";
    }
}
