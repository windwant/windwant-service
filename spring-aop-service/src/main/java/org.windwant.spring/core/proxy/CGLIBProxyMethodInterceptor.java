package org.windwant.spring.core.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.windwant.spring.web.service.Performer;
import org.windwant.spring.web.service.impl.APerformer;

import java.lang.reflect.Method;

/**
 * Created by windwant on 2016/6/4.
 */
public class CGLIBProxyMethodInterceptor implements MethodInterceptor {
    private Enhancer enhancer = new Enhancer();
    public Object getProxy(Class clazz){
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib before action");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("cglib after action");
        return result;
    }

    public static void main(String[] args) {
        CGLIBProxyMethodInterceptor p = new CGLIBProxyMethodInterceptor();
        Performer pp = (Performer) p.getProxy(APerformer.class);
        pp.perform("fsafd");
    }
}
