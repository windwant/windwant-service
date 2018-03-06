package org.windwant.spring.core.proxy;

import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.windwant.spring.web.service.Performer;
import org.windwant.spring.web.service.impl.APerformer;

import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * Created by windwant on 2016/3/4.
 */
public class TestProxy extends TestCase {
    ApplicationContext ctx;

    public void setUp() throws Exception {
        super.setUp();
//        PropertyConfigurator.configure("E:\\javapro\\codetest\\src\\main\\resources\\log4j.properties");
        //ctx = new ClassPathXmlApplicationContext("classpath*:*.xml");
    }

    public void testName() throws Exception {
//        MyJDKProxy mp = new MyJDKProxy();
//        Performer ap = (Performer) mp.getInstance(new APerformer());
//        System.out.println(ap.perform("fadfasd"));

        MyCGLIBProxy p = new MyCGLIBProxy();
        Performer pp = (Performer) p.getProxy(APerformer.class);
        pp.perform("fsafd");
    }

    public void testJavassist(){
        String clazz = "org.windwant.spring.core.proxy.Hello";
        String methodBefore = "{ System.out.println(\"method before...:\"); }";
        String methodAfter = "{ System.out.println(\"method after...:\"); }";
        String pClazz = "org.windwant.spring.core.proxy.HelloP";

        Hello hello = (Hello) new MyJavassistProxy().getProxySelf(clazz, null, "say",
                methodBefore, methodAfter);
        hello.say();
    }

    public void test(){
    }

}
