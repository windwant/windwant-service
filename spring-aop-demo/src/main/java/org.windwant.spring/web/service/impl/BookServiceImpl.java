package org.windwant.spring.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.windwant.spring.web.dao.BookDao;
import org.windwant.spring.web.entity.Book;
import org.windwant.spring.web.service.BookService;

/**
 * Created by windwant on 2016/2/27.
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    public BookDao bookDao;

    public Book getBookById(int id) {
        return bookDao.getBookById(1);
    }

    public void addBookByEntity(Book book) {
        bookDao.addBookByEntity(book);
    }

    public Book updateRel(int id) {
        return bookDao.updateRel(id);
    }
}
