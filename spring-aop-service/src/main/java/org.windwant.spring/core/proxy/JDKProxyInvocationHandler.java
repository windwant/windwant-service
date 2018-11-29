package org.windwant.spring.core.proxy;

import org.windwant.spring.web.service.Performer;
import org.windwant.spring.web.service.impl.APerformer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by windwant on 2016/6/4.
 */
public class JDKProxyInvocationHandler implements InvocationHandler{
    private Object targetObject;
    public Object getInstance(Object targetObject){
        this.targetObject = targetObject;
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(), this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy: before action");
        Object resutl = method.invoke(targetObject, args);
        System.out.println("proxy: after action");
        return resutl;
    }

    public static void main(String[] args) {
        JDKProxyInvocationHandler mp = new JDKProxyInvocationHandler();
        Performer ap = (Performer) mp.getInstance(new APerformer());
        System.out.println(ap.perform("fadfasd"));
    }
}
