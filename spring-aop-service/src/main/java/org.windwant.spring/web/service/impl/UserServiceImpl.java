package org.windwant.spring.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.windwant.spring.web.dao.UserDao;
import org.windwant.spring.web.entity.User;
import org.windwant.spring.web.service.UserService;

/**
 * Created by windwant on 2016/2/27.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    public UserDao userDao;

    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public User insertUser(User user) {
        return userDao.insertUser(user);
    }
}
