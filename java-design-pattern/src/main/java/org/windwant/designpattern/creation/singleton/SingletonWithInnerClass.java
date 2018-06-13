package org.windwant.designpattern.creation.singleton;

/**
 * Created by windwant on 2016/9/19.
 */
public class SingletonWithInnerClass {

    /* ˽�й��췽������ֹ��ʵ���� */
    private SingletonWithInnerClass(){}

    /* �˴�ʹ��һ���ڲ�����ά������ */
    private static class SingletonFactory{
        private static SingletonWithInnerClass singletonWithInnerClass = new SingletonWithInnerClass();
    }

    /* ��ȡʵ�� */
    public static SingletonWithInnerClass getInstance(){
        return SingletonFactory.singletonWithInnerClass;
    }

    /* ����ö����������л������Ա�֤���������л�ǰ�󱣳�һ�� */
    public Object readResolve(){
        return getInstance();
    }

    public void test(){
        System.out.println("singleton method test");
    }
}
