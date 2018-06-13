package org.windwant.designpattern.creation.singleton;

/**
 * Created by windwant on 2016/9/19.
 */
public class SingletonWithLazy {

    private static SingletonWithLazy singletonWithLazy;

    /* ˽�й��췽������ֹ��ʵ���� */
    private SingletonWithLazy(){}

    /* ��ȡʵ�� */
    public static SingletonWithLazy getInstance(){
        if(singletonWithLazy == null){
            singletonWithLazy = new SingletonWithLazy();
        }
        return singletonWithLazy;
    }

    /* ����ö����������л������Ա�֤���������л�ǰ�󱣳�һ�� */
    public Object readResolve(){
        return getInstance();
    }

    public void test(){
        System.out.println("singleton method test");
    }
}
