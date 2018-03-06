import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.windwant.spring.web.entity.Book;
import org.windwant.spring.web.service.BookService;

import javax.annotation.Resource;

/**
 * Created by aayongche on 2016/3/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:applicationContext.xml")
public class TestCtl extends TestCase {

    @Resource
    BookService bookService;
    @Test
    public void testBook(){
        Book b = new Book();
        b.setBookName("bbb");
        b.setPrice(222);
        bookService.addBookByEntity(b);
    }
}
