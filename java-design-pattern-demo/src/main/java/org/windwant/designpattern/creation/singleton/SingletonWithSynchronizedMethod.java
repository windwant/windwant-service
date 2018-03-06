package org.windwant.designpattern.creation.singleton;

/**
 * Created by aayongche on 2016/9/19.
 */
public class SingletonWithSynchronizedMethod {

    private static SingletonWithSynchronizedMethod singletonWithSynchronizedMethod;

    /* 私有构造方法，防止被实例化 */
    private SingletonWithSynchronizedMethod(){}

    /* 此处使用一个内部类来维护单例 */
    private static synchronized void initInstance(){
        if(singletonWithSynchronizedMethod == null) {
            singletonWithSynchronizedMethod = new SingletonWithSynchronizedMethod();
        }
    }

    /* 获取实例 */
    public static SingletonWithSynchronizedMethod getInstance(){
        if(singletonWithSynchronizedMethod == null){
            initInstance();
        }
        return singletonWithSynchronizedMethod;
    }

    /* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
    public Object readResolve(){
        return getInstance();
    }

    public void test(){
        System.out.println("singleton method test");
    }
}
