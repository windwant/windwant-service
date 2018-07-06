package org.windwant.nativemethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Hello world!
 *
 */
public class ExecWinNativeMethod
{
    public static void main( String[] args ){
        System.out.println(findProcess("TIM.exe"));
    }

    /**
     * 打开记事本
     */
    public static void openNotePad(){
        try {
            Process process = new ProcessBuilder("notepad").start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开记事本
     */
    public static void openNotePad1(){
        try {
            Process process = Runtime.getRuntime().exec("notepad");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动浏览器
     */
    public static void explore(){
        try {

            String exeFullPathName="C:/Program Files/Internet Explorer/IEXPLORE.EXE";
            String message="www.baidu.com";
            String []cmd={exeFullPathName,message};
            Process proc = Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 任务管理器
     */
    public static void taskList(){
        BufferedReader br = null;
        try {
            Process process = Runtime.getRuntime().exec("tasklist");
            br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = br.readLine()) != null){
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 查找任务进程
     * @param processName
     * @return
     */
    public static boolean findProcess(String processName){
        BufferedReader br=null;
        try{
            Process proc=Runtime.getRuntime().exec("tasklist");
            br=new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line=null;
            while((line=br.readLine())!=null){
                if(line.contains(processName)){
                    return true;
                }
            }

            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally{
            if(br!=null){
                try{
                    br.close();
                }catch(Exception ex){
                }
            }

        }
    }
}
