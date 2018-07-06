package org.windwant.spring.core.proxy;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * Created by windwant on 2016/9/18.
 */
public class MyJavassistProxy {

    public Object getProxySelf(String clazz, String pClazz, String methodName, String methodBefore, String methodAfter){
        ClassPool cp = ClassPool.getDefault();
        CtClass ct;
        try {
            ct = cp.get(clazz);
            if(pClazz != null){
                ct.setSuperclass(cp.get(pClazz));
            }
            ct.writeFile();
            ct.defrost();
            CtMethod m = ct.getDeclaredMethod(methodName);
            if(methodBefore != null) {
                m.insertBefore(methodBefore);
            }
            if(methodAfter != null) {
                m.insertAfter(methodAfter);
            }
            Class c = ct.toClass();
            return c.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
