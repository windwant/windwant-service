package com.thread.test.thread;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * ForkJoinPool 负责执行ForkJoinTask，通常继承以下两个类
 * RecursiveTask 带返回值的ForkJoinTask
 * RecursiveAction 不带返回值的ForkJoinTask
 *
 * Created by windwant on 2016/6/3.
 */
public class MyForkJoin {

    public static void main(String[] args) {
        MyTask task = new MyTask(new File("D:\\MPS"));
        Integer sum = new ForkJoinPool().invoke(task);
        System.out.println(sum);
    }
}

class MyTask extends RecursiveTask<Integer>{

    public Integer num = 0;

    private File file;
    MyTask(File file){
        this.file = file;
    }

    @Override
    protected Integer compute() {
        List<MyTask> taskList = new ArrayList<MyTask>();
        if(file.isDirectory()){
            File[] list = file.listFiles();
            for(File subf: list){
                if(subf.isDirectory()){
                    MyTask mt = new MyTask(subf);
                    taskList.add(mt);
                }else{
                    num++;
                    System.out.println(subf.toString());
                }
            }
        }else{
            num = 1;
        }

        if(!taskList.isEmpty()){
            //同下
//            for(MyTask mtask: taskList){
//                mtask.fork();//
//            }
//            for(MyTask mtask: taskList){
//                num += mtask.join();
//            }

            for(MyTask mtask: invokeAll(taskList)){ //invokeAll() 返回ForkJoinTask集合 fork() 把任务加入workqueue
                num += mtask.join();
            }
        }
        return num;
    }
}
