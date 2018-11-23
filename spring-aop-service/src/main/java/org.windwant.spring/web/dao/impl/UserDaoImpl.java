package org.windwant.spring.web.dao.impl;

import org.springframework.stereotype.Repository;
import org.windwant.spring.core.hibernate.TBaseDaoImpl;
import org.windwant.spring.web.dao.UserDao;
import org.windwant.spring.web.entity.User;

/**
 * Created by windwant on 2016/3/4.
 */
@Repository
public class UserDaoImpl extends TBaseDaoImpl implements UserDao {

    public User getUserById(int id) {
        return hibernateTemplate.get(User.class, id);
    }

    @Override
    public User insertUser(User user) {
        hibernateTemplate.saveOrUpdate(user);
        return user;
    }
}
