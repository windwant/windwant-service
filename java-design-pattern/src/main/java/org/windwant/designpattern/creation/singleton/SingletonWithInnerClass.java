package org.windwant.designpattern.creation.singleton;

/**
 * Created by aayongche on 2016/9/19.
 */
public class SingletonWithInnerClass {

    /* 私有构造方法，防止被实例化 */
    private SingletonWithInnerClass(){}

    /* 此处使用一个内部类来维护单例 */
    private static class SingletonFactory{
        private static SingletonWithInnerClass singletonWithInnerClass = new SingletonWithInnerClass();
    }

    /* 获取实例 */
    public static SingletonWithInnerClass getInstance(){
        return SingletonFactory.singletonWithInnerClass;
    }

    /* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
    public Object readResolve(){
        return getInstance();
    }

    public void test(){
        System.out.println("singleton method test");
    }
}
