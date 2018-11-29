package org.windwant.spring.core.proxy;

import javassist.*;

import javax.el.MethodNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * javassist opt
 * Created by windwant on 2016/9/18.
 */
public class JavassistUtil {

    /**
     * 生成类代理
     * @param clazz 类全限定名
     * @param pClazz 父类全限定名
     * @param methodName 方法名称
     * @param methodBefore 方法前操作
     * @param methodAfter 方法后操作
     * @param src 类文件保存路径， 默认当前项目根路径
     * @return
     */
    public static Object getProxyInstance(String clazz, String pClazz, String methodName, String methodBefore, String methodAfter, String src){
        if(clazz == null || clazz == "") return null;
        //CtClass池
        ClassPool cp = ClassPool.getDefault();
        CtClass ct;
        try {
            ct = cp.getOrNull(clazz);
            //不存在则创建
            if(ct == null){
                ct = cp.makeClass(clazz);
            }
            //接口判断
            if(ct.isInterface()) return null;

            if(pClazz != null && cp.find(pClazz) != null){
                CtClass superClass = cp.get(pClazz);
                if(!superClass.isInterface()) {
                    ct.setSuperclass(cp.get(pClazz));
                }

            }
            //写完文件后，ct上将不能再做更改
            if(src != null && src != "") {
                ct.writeFile(src);
            }else {
                ct.writeFile();
            }
            //Defrosts 操作使得ct可以重新修改
            ct.defrost();

            if(methodName != null && methodName != "") {

                CtMethod m;
                try {
                    m = ct.getDeclaredMethod(methodName);
                } catch (MethodNotFoundException e) {
                    m = CtMethod.make(methodName, ct);
                }
                //方法不存在则添加
                if (m != null) {
                    ct.addMethod(m);
                }
                //在方法前添加代码段
                if (methodBefore != null) {
                    m.insertBefore(methodBefore);
                }
                //在方法后添加代码段
                if (methodAfter != null) {
                    m.insertAfter(methodAfter);
                }
            }
            return ct.toClass().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getProxyInstance(String clazz, String pClazz, String methodName, String methodBefore, String methodAfter) {
        return getProxyInstance(clazz, pClazz, methodName, methodBefore, methodAfter, null);
    }

    public static Object getProxyInstance(String clazz, String pClazz, String methodName) {
        return getProxyInstance(clazz, pClazz, methodName, null, null, null);
    }

    public static Object getProxyInstance(String clazz, String pClazz) {
        return getProxyInstance(clazz, pClazz, null, null, null, null);
    }

    public static void main(String[] args) {
//        System.out.println(getProxyInstance("org.windwant.spring.web.service.BookService", null, null, null, null));
        String methodBefore = "{ System.out.println(\"method before...:\"); }";
        String methodAfter = "{ System.out.println(\"method after...:\"); }";
        System.out.println(getProxyInstance("org.windwant.TestProxyClass", "org.windwant.spring.core.proxy.Hello", null, null, null));
//        Class test = (Class) getClassOrInstance("org.windwant.TestClass", new HashMap() {{
//            put("name", "java.lang.String");
//            put("age", "int");
//            put("sex", "int");
//        }}, true);
//        try {
//            Constructor c = test.getDeclaredConstructor(new Class[]{String.class, int.class, int.class});
//            Object testObject = c.newInstance("lilei", 20, 1);
//            System.out.println(test);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
    }


    /**
     * 生成类对象
     * @param clazz 类全限定名
     * @param fields 字段属性
     * @param extraConstructor 是否需要带参数的构造函数
     * @return
     */
    public static Object getClassOrInstance(String clazz, Map<String, String> fields, boolean extraConstructor, boolean instance){
        // 创建类 ClassPool CtClass 容器；CtClass必须由此容器获得；ClassPool存储所有创建的CtClass对象，有利于保障类修改的一致性；大量的类会耗费很多内存，因此，推荐分批次，操作，并重建ClassPool
        ClassPool pool = ClassPool.getDefault();
        //由类的全限定名创建，如果已经有同名的类，则覆盖
        CtClass cls = pool.makeClass(clazz);

        List<CtClass> cs = new ArrayList();
        StringBuilder conBody = null;

        if(extraConstructor) {
            new StringBuilder();
            conBody.append("{\r\n");
        }

        // 属性 getter、setter方法
        final int[] i = {1};
        fields.entrySet().stream().forEach(item -> {
            CtField param = null; //name属性
            try {
                param = new CtField(pool.get(item.getValue()), item.getKey(), cls);
                param.setModifiers(Modifier.PRIVATE); //访问控制

                //CtNewMethod CtMethod创建工具
                String firstUpper = String.valueOf(item.getKey().charAt(0)).toUpperCase() + item.getKey().substring(1);
                cls.addMethod(CtNewMethod.setter("set" + firstUpper, param));
                cls.addMethod(CtNewMethod.getter("get" + firstUpper, param));
                cls.addField(param);

                if (extraConstructor) {
                    cs.add(pool.get(item.getValue()));
                    conBody.append("$0." + item.getKey() + " = $" + i[0] + ";"); //$num 参数 $0指代this
                }
                i[0]++;
            } catch (CannotCompileException e) {
                e.printStackTrace();
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        });

        //构造函数
        CtConstructor cons = new CtConstructor(new CtClass[] {}, cls);
        try {
            cons.setBody(null);
            cls.addConstructor(cons);

            // 添加有参的构造体
            if(extraConstructor) {
                conBody.append("\r\n}");
                cons = new CtConstructor(cs.toArray(new CtClass[0]), cls);
                cons.setBody(conBody.toString());
                cls.addConstructor(cons);
            }

            //将生成的类保存到文件
            cls.writeFile();
            if(instance){
                return cls.toClass().newInstance();
            }
            return cls.toClass();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成类对象
     * @param clazz
     * @param fields
     * @return
     */
    public static Object getClassOrInstance(String clazz, Map<String, String> fields){
        return getClassOrInstance(clazz, fields, false, false);
    }

    /**
     * 生成类对象
     * @param clazz
     * @param fields
     * @return
     */
    public static Object getClassOrInstance(String clazz, Map<String, String> fields, boolean extraConstructor){
        return getClassOrInstance(clazz, fields, extraConstructor, false);
    }
}
