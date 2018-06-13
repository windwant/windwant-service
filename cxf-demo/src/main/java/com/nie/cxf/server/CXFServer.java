package com.nie.cxf.server;

import com.nie.cxf.server.CXFDemoImpl;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import javax.xml.ws.Endpoint;

/**
 * Created by windwant on 2016/1/7.
 */
public class CXFServer {
    private static final String ADDRESS = "http://localhost:9001/cxfdemo";

    public CXFServer() {
        System.out.println("Starting Server");
        CXFDemoImpl demo = new CXFDemoImpl();

        Endpoint.publish(ADDRESS, demo);
        System.out.println("Start success");
    }

    public static void main(String[] args){
        new CXFServer();
        System.out.println("server....");
    }
}
