package org.windwant.spring.web.service;

import org.windwant.spring.web.entity.User;

/**
 * Created by windwant on 2016/2/27.
 */
public interface UserService {
    User getUserById(int id);

    User insertUser(User user);
}
