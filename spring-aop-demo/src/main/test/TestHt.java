import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.windwant.spring.web.entity.User;
import org.windwant.spring.web.service.UserService;


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
        UserService us = (UserService) ctx.getBean("uservice");
        User user = us.getUserById(1);
        System.out.println(user.getUserName());
    }

}
