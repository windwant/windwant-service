package com.thread.test.thread.forkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * 统计文件数量
 */
public class MyCalcFileNumTask extends RecursiveTask<Integer>{

    public Integer num = 0;

    private File file;
    public MyCalcFileNumTask(File file){
        this.file = file;
    }

    @Override
    protected Integer compute() {
        List<MyCalcFileNumTask> taskList = new ArrayList<MyCalcFileNumTask>();
        if(file.isDirectory()){
            File[] list = file.listFiles();
            for(File subf: list){
                if(subf.isDirectory()){
                    MyCalcFileNumTask mt = new MyCalcFileNumTask(subf);
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
//            for(MyCalcFileNumTask mtask: taskList){
//                mtask.fork();//
//            }
//            for(MyCalcFileNumTask mtask: taskList){
//                num += mtask.join();
//            }

            for(MyCalcFileNumTask mtask: invokeAll(taskList)){ //invokeAll() 返回ForkJoinTask集合 fork() 把任务加入workqueue
                num += mtask.join();//区别于 get()，处理了异常
            }
        }
        return num;
    }
}
