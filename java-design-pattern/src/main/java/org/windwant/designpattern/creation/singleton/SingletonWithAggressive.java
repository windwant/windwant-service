package org.windwant.designpattern.creation.singleton;

/**
 * Created by windwant on 2016/9/19.
 */
public class SingletonWithAggressive {

    private static final SingletonWithAggressive singletonWithAggressive = new SingletonWithAggressive();

    /* ˽�й��췽������ֹ��ʵ���� */
    private SingletonWithAggressive(){}

    /* ��ȡʵ�� */
    public static SingletonWithAggressive getInstance(){
        return singletonWithAggressive;
    }

    /* ����ö����������л������Ա�֤���������л�ǰ�󱣳�һ�� */
    public Object readResolve(){
        return getInstance();
    }

    public void test(){
        System.out.println("singleton method test");
    }
}
