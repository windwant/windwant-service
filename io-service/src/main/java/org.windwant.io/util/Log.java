package org.windwant.io.util;

/**
 * Created by Administrator on 18-10-30.
 */
public class Log {

    private String clsName;

    private Log(){}

    public static Log getLogger(String cls){
        Log log = new Log();
        log.clsName = cls;
        return log;
    }

    public void info(String msg){
        System.out.println(clsName + " --- " + msg);
    }
}
