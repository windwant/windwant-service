package org.windwant.spring.web.controller;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.windwant.spring.web.entity.Book;
import org.windwant.spring.web.service.BookService;

import javax.validation.Valid;

/**
 * Created by windwant on 2016/3/4.
 */
@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;

    @RequestMapping("addBook.do")
    @ResponseBody
    public Book addBook(@RequestParam @Valid String bookName, @RequestParam @Valid int price){
        LogManager.getLogger().info(bookName + " : " + price);
        Book b = new Book();
        b.setBookName(bookName);
        b.setPrice(price);
        bookService.addBookByEntity(b);
        return b;
    }

    @RequestMapping("testRel.do")
    @ResponseBody
    public String updateRel(int id){
        return JSON.toJSONString(bookService.updateRel(id));
    }


    @RequestMapping("index.do")
    public String index(){
        return "redirect:/";
    }
}
