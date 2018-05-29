package org.windwant.io.io;

import java.io.File;

/**
 * Created by Administrator on 18-5-29.
 */
public class FileStream {
    public void delete(String file){
        File tmp = new File(file);
        if(tmp.isDirectory()){
            for (File s : tmp.listFiles()) {
                delete(s);
            }
            tmp.delete();
        }else {
            tmp.delete();
        }
    }

    public void delete(File file){
        if(file.isDirectory()){
            for (File s : file.listFiles()) {
                delete(s);
            }
            file.delete();
        }else {
            file.delete();
        }
    }

}
