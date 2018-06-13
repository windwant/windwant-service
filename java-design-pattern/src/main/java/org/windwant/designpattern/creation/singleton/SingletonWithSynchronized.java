package org.windwant.designpattern.creation.singleton;

/**
 * Created by windwant on 2016/9/19.
 */
public class SingletonWithSynchronized {

    private static SingletonWithSynchronized singletonWithSynchronized;
    private static int count = 0;

    /* ˽�й��췽������ֹ��ʵ���� */
    private SingletonWithSynchronized(){}

    /* �˴�ʹ��һ���ڲ�����ά������ */
    private static synchronized void initInstance(){
        if(singletonWithSynchronized == null) {
            singletonWithSynchronized = new SingletonWithSynchronized();
            count++;
        }
    }

    /* ��ȡʵ�� */
    public static SingletonWithSynchronized getInstance(){
        if(singletonWithSynchronized == null){
            synchronized (SingletonWithSynchronized.class) {
                initInstance();
            }
        }
        return singletonWithSynchronized;
    }

    /* ����ö����������л������Ա�֤���������л�ǰ�󱣳�һ�� */
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
