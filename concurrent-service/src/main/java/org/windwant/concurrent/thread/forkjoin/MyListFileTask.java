package org.windwant.concurrent.thread.forkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * 列显文件夹下的文件
 */
public class MyListFileTask extends RecursiveTask<List<String>>{

    public List<String> files = new ArrayList<>();

    private File file;
    public MyListFileTask(File file){
        this.file = file;
    }

    @Override
    protected List<String> compute() {
        List<String> files = new ArrayList<>();
        List<MyListFileTask> taskList = new ArrayList<MyListFileTask>();
        if(file.isDirectory()){
            File[] list = file.listFiles();
            for(File subf: list){
                if(subf.isDirectory()){
                    MyListFileTask mt = new MyListFileTask(subf);
                    taskList.add(mt);
                }else{
                    files.add(subf.toString());
                }
            }
        }

        if(!taskList.isEmpty()){
            //同下
//            for(MyCalcFileNumTask mtask: taskList){
//                mtask.fork();//
//            }
//            for(MyCalcFileNumTask mtask: taskList){
//                num += mtask.join();
//            }

            for(MyListFileTask mtask: invokeAll(taskList)){ //invokeAll() 返回ForkJoinTask集合 fork() 把任务加入workqueue
                files.addAll(mtask.join());
            }
        }
        return files;
    }
}
