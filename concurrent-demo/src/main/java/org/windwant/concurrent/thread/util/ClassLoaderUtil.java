package org.windwant.concurrent.thread.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义类加载工具
 * Created by Administrator on 18-5-11.
 */
public class ClassLoaderUtil {
    static ClassLoader myClassLoader;
    static {
        myClassLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                String className = name.substring(name.lastIndexOf(".") + 1) + ".class";
                InputStream in = getClass().getResourceAsStream(className);
                if (in == null) {
                    return super.loadClass(name);
                }
                try {
                    byte[] b = new byte[in.available()];
                    in.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                return super.findClass(name);
            }
        };
    }

    public static Object loadClass(String classPath){
        try {
            return myClassLoader.loadClass(classPath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(loadClass("org.windwant.concurrent.thread.synlist.MySynList"));
    }
}
