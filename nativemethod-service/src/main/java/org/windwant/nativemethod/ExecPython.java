package org.windwant.nativemethod;

import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2018/1/15.
 */
public class ExecPython {

    private static void init(){
        Properties props = new Properties();
        props.put("python.import.site", "false");
        PythonInterpreter.initialize(System.getProperties(), props, new String[0]);
    }

    public static void execPyScript(){
        init();
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("days=('mod','Tue','Wed','Thu','Fri','Sat','Sun'); ");   ///执行python脚本
        interpreter.exec("print days[1];");
    }

    /**
     * 执行脚本 传递参数 并返回值
     */
    public static void execPyScriptFile(){
        init();
        PythonInterpreter interpreter = new PythonInterpreter();
        InputStream py = null;
        try {
            py = ExecPython.class.getClassLoader().getResourceAsStream("test.py");
            interpreter.execfile(py);  ///执行python py文件

            PyFunction pyFunction = interpreter.get("week", PyFunction.class); // 第一个参数为期望获得的函数（变量）的名字，第二个参数为期望返回的对象类型
            PyObject pyObject = pyFunction.__call__(PyString.fromInterned("this is the param passed")); // 调用函数 传递参数 PyObject
            System.out.println(pyObject);
        } finally {
            try {
                if(py != null) {
                    py.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        execPyScript();
    }
}
