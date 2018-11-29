package org.windwant.netty.serialize;

import java.io.Serializable;

/**
 * 请求对象
 * Created by windwant on 2016/6/14.
 */
public class SubscribReq implements Serializable {

    private static final Long serialVersionUID = 1L;

    private int subReqID;

    private String userName;

    private String productName;

    private String phoneNumber;

    private String address;

    public int getSubReqID() {
        return subReqID;
    }

    public void setSubReqID(int subReqID) {
        this.subReqID = subReqID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String toString(){
        return "SubscribReq [subReqID: " + subReqID
                + ", userName: " + userName
                + ", productName: " + productName
                + ", phoneNumber: " + phoneNumber
                + ", address: " + address
                + "]";
    }
}
