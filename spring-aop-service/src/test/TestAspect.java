import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.windwant.spring.web.service.Performer;
import org.windwant.spring.web.service.PerformerCallHelp;
import org.windwant.spring.web.service.PerformerSmile;

/**
 * Created by windwant on 2016/2/24.
 */
public class TestAspect {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:*.xml");

        Performer performer = (Performer)ctx.getBean("per");
        performer.count((int) (Math.random() * 10));
        performer.perform("piano");
        performer.myOwn();
        //performer.slient();
        ((PerformerSmile)performer).smile();
        ((PerformerCallHelp)performer).callHelp();
    }
}
