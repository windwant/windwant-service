package org.windwant.spring.web.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by windwant on 2016/3/8.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="javaClassName")
public class Book implements Serializable {

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getPrice() {
        return price;
    }


    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Id
    @GeneratedValue(generator = "bookGenerator")
    @GenericGenerator(name = "bookGenerator", strategy = "native")
    private int id;
    @Column(name = "book_name")
    private String bookName;
    @Column
    private int price;
    @ManyToOne(cascade = CascadeType.PERSIST,targetEntity = User.class)
    private User user;
}
