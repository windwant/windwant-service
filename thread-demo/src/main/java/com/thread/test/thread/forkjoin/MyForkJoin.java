package com.thread.test.thread.forkjoin;

import java.io.File;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * ForkJoinPool 负责执行ForkJoinTask，通常继承以下两个类
 * RecursiveTask 带返回值的ForkJoinTask
 * RecursiveAction 不带返回值的ForkJoinTask
 *
 * 统计路径下文件个数
 * Created by windwant on 2016/6/3.
 */
public class MyForkJoin {

    public static void main(String[] args) {
        testListFileNum();
    }

    public static void testCalcFileNum(){
        MyCalcFileNumTask task = new MyCalcFileNumTask(new File("D:\\MPS"));
        Integer sum = new ForkJoinPool().invoke(task);
        System.out.println(sum);
    }

    public static void testListFileNum(){
        MyListFileNumTask task = new MyListFileNumTask(new File("D:\\MPS"));
        List<String> files = new ForkJoinPool().invoke(task);
        files.stream().forEach(file-> System.out.println(file));
    }
}

