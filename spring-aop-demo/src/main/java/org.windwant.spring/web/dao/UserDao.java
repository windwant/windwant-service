package org.windwant.spring.web.dao;

import org.windwant.spring.core.hibernate.TBaseDao;
import org.windwant.spring.web.entity.User;

/**
 * Created by windwant on 2016/3/4.
 */
public interface UserDao extends TBaseDao {

    User getUserById(int id);
}
