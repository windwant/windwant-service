package org.windwant.spring.web.service;

import org.windwant.spring.web.entity.Book;

/**
 * Created by windwant on 2016/2/27.
 */
public interface BookService {
    Book getBookById(int id);
    void addBookByEntity(Book book);
    Book updateRel(int id);
}
