package edu.sandau.chat.service;

import edu.sandau.chat.entity.User;
import edu.sandau.chat.enums.LoginValueEnum;

public interface UserService {

    /***
     * 若登录失败，返回null
     * @param loginValue
     * @param name
     * @param password
     * @return
     */
    User login(LoginValueEnum loginValue, String name, String password);

    /***
     * 用户注册
     * @param user
     * @return
     */
    User register(User user);

}
