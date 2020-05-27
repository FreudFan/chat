package edu.sandau.chat.controller;

import edu.sandau.chat.entity.User;
import edu.sandau.chat.enums.LoginValueEnum;
import edu.sandau.chat.security.RequestContent;
import edu.sandau.chat.service.UserService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public User login(User user) throws Exception {
        String name = user.getName();
        String password = user.getPassword();
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
            return null;
        }
        LoginValueEnum loginValue;

        if (name.contains("@")) {
            //识别是否是邮箱
            loginValue = LoginValueEnum.EMAIL;
        } else if (name.length() == 11 && NumberUtils.isDigits(name)) {
            //识别是手机号
            loginValue = LoginValueEnum.TELEPHONE;
        } else {
            loginValue = LoginValueEnum.USERNAME;
        }
        user = userService.login(loginValue, name, password);
        if (user == null) {
            return null;
        }

//        RequestContent.add(user);
        return user;
    }
}
