package org.windwant.concurrent.thread.forkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

/**
 * 按文件类型统计 列显
 */
public class MyCalcAndListFileTask extends RecursiveTask<Map<String, List<String>>>{

    public Map<String, List<String>> files = new HashMap<>();

    private File file;
    public MyCalcAndListFileTask(File file){
        this.file = file;
    }

    private void putItem(String key, String value, Map<String, List<String>> map){
        if(map.get(key) == null){
            map.put(key, new ArrayList(){{add(value);}});
        }
        map.get(key).add(value);
    }

    @Override
    protected Map<String, List<String>> compute() {
        Map<String, List<String>> files = new HashMap<>();
        List<MyCalcAndListFileTask> taskList = new ArrayList<MyCalcAndListFileTask>();
        if(file.isDirectory()){
            File[] list = file.listFiles();
            for(File subf: list){
                if(subf.isDirectory()){
                    MyCalcAndListFileTask mt = new MyCalcAndListFileTask(subf);
                    taskList.add(mt);
                }else{
                    int dotIndex = subf.getName().lastIndexOf(".");
                    String type = "others";
                    if(dotIndex != -1){
                        type = subf.getName().substring(subf.getName().lastIndexOf(".") + 1, subf.getName().length());
                    }
                    putItem(type, subf.toString(), files);
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

            for(MyCalcAndListFileTask mtask: invokeAll(taskList)){ //invokeAll() 返回ForkJoinTask集合 fork() 把任务加入workqueue
                files.putAll(mtask.join());
            }
        }
        return files;
    }
}
