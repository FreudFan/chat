package edu.sandau.chat.service.impl;

import edu.sandau.chat.entity.User;
import edu.sandau.chat.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Test
    void login() {
    }

    @Test
    void register() {
        User user = new User();
        user.setName("11111");
        user.setEmail("123@123.com");
        user.setTelephone("12345678912");
        user.setGender(1);
        user.setPassword("123");
        user = userService.register(user);
        System.out.println(user);
    }

    @Test
    void check() {
    }
}