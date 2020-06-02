package edu.sandau.chat.controller;

import edu.sandau.chat.interceptor.RequestContent;
import edu.sandau.chat.service.UserService;
import edu.sandau.chat.vo.MyFriendsVO;
import edu.sandau.chat.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /***
     * 搜索用户
     * @param name 模糊搜索 用户名
     * @return 不会查询到自己
     */
    @GetMapping("/queryUser")
    public List<UserVO> queryUser(String name) {
        List<UserVO> users =  userService.searchUser(name);
        return users;
    }

    /***
     * 查询好友列表
     * @return
     */
    @GetMapping("/friend")
    public MyFriendsVO myFriends() {
        //TODO 查询好友列表
        userService.queryMyFriends(RequestContent.getCurrentUser().getId());
        return null;
    }
}
