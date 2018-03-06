package org.windwant.spring.web.dao;

import org.windwant.spring.core.hibernate.TBaseDao;
import org.windwant.spring.web.entity.Book;

/**
 * Created by windwant on 2016/3/4.
 */
public interface BookDao extends TBaseDao {

    Book getBookById(int id);
    void addBookByEntity(Book book);
    Book updateRel(int id);

}
