package com.nie.cxf.outinterface;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.ResourceProvider;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by windwant on 2016/1/13.
 */
public class CXFRESTServer {

    public static final String ADDRESS = "http://localhost:9009/restdemo";
    public CXFRESTServer() {
        // ��� ResourceClass
        List<Class<?>> resourceClassList = new ArrayList<Class<?>>();
        resourceClassList.add(CXFServiceImpl.class);

        // ��� ResourceProvider
        List<ResourceProvider> resourceProviderList = new ArrayList<ResourceProvider>();
        resourceProviderList.add(new SingletonResourceProvider(new CXFServiceImpl()));

        // ��� Provider
        List<Object> providerList = new ArrayList<Object>();
        providerList.add(new JacksonJsonProvider());

        JAXRSServerFactoryBean js = new JAXRSServerFactoryBean();
        js.setAddress(ADDRESS);
        js.setResourceClasses(resourceClassList);
        js.setResourceProviders(resourceProviderList);
        js.setProviders(providerList);
        js.create();
        System.out.println("rest service is published");

//        JAXRSServerFactoryBean js = new JAXRSServerFactoryBean();
//        js.setAddress(ADDRESS);
//        js.setServiceClass(CXFServiceImpl.class);
//        js.setResourceClasses(CXFServiceImpl.class);
//        js.create();
    }

    public static void main(String[] args){
        new CXFRESTServer();
    }
}
