package org.windwant.designpattern.creation.singleton;

/**
 * Created by aayongche on 2016/9/19.
 */
public class SingletonWithSynchronized {

    private static SingletonWithSynchronized singletonWithSynchronized;
    private static int count = 0;

    /* 私有构造方法，防止被实例化 */
    private SingletonWithSynchronized(){}

    /* 此处使用一个内部类来维护单例 */
    private static synchronized void initInstance(){
        if(singletonWithSynchronized == null) {
            singletonWithSynchronized = new SingletonWithSynchronized();
            count++;
        }
    }

    /* 获取实例 */
    public static SingletonWithSynchronized getInstance(){
        if(singletonWithSynchronized == null){
            synchronized (SingletonWithSynchronized.class) {
                initInstance();
            }
        }
        return singletonWithSynchronized;
    }

    /* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
    public Object readResolve(){
        return getInstance();
    }

    public void test(){
        System.out.println("singleton method test: " + count);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++)
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SingletonWithSynchronized.getInstance().test();
                }
            }).start();
        System.out.println(SingletonWithSynchronized.getInstance().equals(SingletonWithSynchronized.getInstance()));
    }
}
