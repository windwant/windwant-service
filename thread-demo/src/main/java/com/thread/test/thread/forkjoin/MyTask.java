package com.thread.test.thread.forkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Integer>{

    public Integer num = 0;

    private File file;
    public MyTask(File file){
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
