package edu.sandau.chat.controller;

import edu.sandau.chat.entity.User;
import edu.sandau.chat.enums.LoginTypeEnum;
import edu.sandau.chat.exception.LoginException;
import edu.sandau.chat.interceptor.RequestContent;
import edu.sandau.chat.service.UserService;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public User login(@RequestBody Map<String, String> param) {
        String text = MapUtils.getString(param, "text", null);
        String password = MapUtils.getString(param, "password", null);
        if(StringUtils.isEmpty(text) || StringUtils.isEmpty(password)) {
            throw new LoginException("文本框输入格式错误");
        }
        LoginTypeEnum loginValue;
        if (text.contains("@")) {
            //识别是否是邮箱
            loginValue = LoginTypeEnum.EMAIL;
        } else if (text.length() == 11 && NumberUtils.isDigits(text)) {
            //识别是手机号
            loginValue = LoginTypeEnum.TELEPHONE;
        } else {
            throw new LoginException("文本框输入格式错误");
        }
        User user = userService.login(loginValue, text, password);
        if(user != null) {
            RequestContent.add(user);
            return user;
        } else {
            throw new LoginException("用户名或密码错误");
        }
    }

    /***
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        user = userService.register(user);
        return user;
    }

}
