package edu.sandau.chat.service.impl;

import edu.sandau.chat.dao.UserDao;
import edu.sandau.chat.entity.User;
import edu.sandau.chat.enums.LoginValueEnum;
import edu.sandau.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User login(LoginValueEnum loginValue, String loginName, String password) {
        User user = userDao.getMapper().login(loginValue.getName(), loginName, password);
        return user;
    }

    @Override
    public User register(User user) {
        return null;
    }

}
