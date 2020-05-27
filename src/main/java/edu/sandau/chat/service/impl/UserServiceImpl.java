package edu.sandau.chat.service.impl;

import edu.sandau.chat.dao.UserDao;
import edu.sandau.chat.entity.User;
import edu.sandau.chat.enums.LoginTypeEnum;
import edu.sandau.chat.exception.RegisterException;
import edu.sandau.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User login(LoginTypeEnum loginValue, String loginName, String password) {
        User user = userDao.getMapper().login(loginValue.getName(), loginName, password);
        return user;
    }

    @Override
    public User register(User user) {
        String telephone = user.getTelephone();
        String email = user.getEmail();
        if(this.check(LoginTypeEnum.TELEPHONE, telephone)
                && this.check(LoginTypeEnum.EMAIL, email)) {
            return userDao.getRepository().save(user);
        } else {
            throw new RegisterException("用户名或手机号已存在");
        }
    }

    @Override
    public boolean check(LoginTypeEnum loginTypeEnum, String value) {
        return userDao.getMapper().count(loginTypeEnum.getName(), value)  == 0;
    }
}
