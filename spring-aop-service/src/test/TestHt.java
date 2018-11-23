import junit.framework.TestCase;

import org.junit.Assert;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.windwant.spring.web.entity.User;
import org.windwant.spring.web.service.UserService;

import java.util.concurrent.ThreadLocalRandom;


/**
 * Created by windwant on 2016/3/4.
 */
public class TestHt extends TestCase {
    ApplicationContext ctx;

    public void setUp() throws Exception {
        super.setUp();
//        PropertyConfigurator.configure("E:\\javapro\\codetest\\src\\main\\resources\\log4j.properties");
        ctx = new ClassPathXmlApplicationContext("classpath*:*.xml");
    }

    public void testName() throws Exception {
        UserService us = (UserService) ctx.getBean("userService");
        User user = new User();
        user.setUserName("test" + ThreadLocalRandom.current().nextInt(100));
        user.setPasswd(String.valueOf(Math.random()));
        user.setSex("1");
        us.insertUser(user);
        User load = us.getUserById(1);
        Assert.assertNotNull(load);
        System.out.println(user.toString());
    }

}
