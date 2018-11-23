package org.windwant.spring.web.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by windwant on 2016/2/27.
 */
@Entity
@XmlRootElement
public class User implements Serializable{
    public int getId() {
        return id;
    }

    @Column
    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswd() {
        return passwd;
    }


    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }


    @Id
    @GeneratedValue(generator = "bookGenerator")
    @GenericGenerator(name = "bookGenerator", strategy = "native")
    int id;
    @Column(name = "user_name")
    @Pattern(regexp = "[A-Za-z0-9]{5,20}", message = "{username.illegal}")
    @NotNull(message = "The userName of user can not be null")
    String userName;
    @Column
    @NotNull(message = "The sex of user can not be null")
    String passwd;
    @Column
    String sex;
    @OneToMany(cascade = CascadeType.PERSIST, targetEntity = Book.class, mappedBy = "user")
    @OrderBy("id")
    @Transient
    List<Book> bookList;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passwd='" + passwd + '\'' +
                ", sex='" + sex + '\'' +
                ", bookList=" + bookList +
                '}';
    }
}
