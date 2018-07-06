package org.windwant.spring.web.dao.impl;

import org.springframework.stereotype.Repository;
import org.windwant.spring.core.hibernate.TBaseDaoImpl;
import org.windwant.spring.web.dao.BookDao;
import org.windwant.spring.web.entity.Book;
import org.windwant.spring.web.entity.User;

/**
 * Created by windwant on 2016/3/4.
 */
@Repository
public class BookDaoImpl extends TBaseDaoImpl implements BookDao {

    public Book getBookById(int id) {
        return hibernateTemplate.get(Book.class, id);
    }

    public void addBookByEntity(Book book) {
        hibernateTemplate.saveOrUpdate(book);
    }

    public Book updateRel(int id) {
        Book b = hibernateTemplate.load(Book.class, id);
        User u = hibernateTemplate.load(User.class, 1);
        b.setUser(u);
        hibernateTemplate.saveOrUpdate(b);
        return hibernateTemplate.load(Book.class, id);
    }
}
