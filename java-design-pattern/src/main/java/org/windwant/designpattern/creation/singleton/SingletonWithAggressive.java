package org.windwant.designpattern.creation.singleton;

/**
 * Created by aayongche on 2016/9/19.
 */
public class SingletonWithAggressive {

    private static final SingletonWithAggressive singletonWithAggressive = new SingletonWithAggressive();

    /* 私有构造方法，防止被实例化 */
    private SingletonWithAggressive(){}

    /* 获取实例 */
    public static SingletonWithAggressive getInstance(){
        return singletonWithAggressive;
    }

    /* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
    public Object readResolve(){
        return getInstance();
    }

    public void test(){
        System.out.println("singleton method test");
    }
}
