package edu.sandau.chat.controller;

import edu.sandau.chat.entity.group.Group;
import edu.sandau.chat.entity.user.User;
import edu.sandau.chat.interceptor.RequestContent;
import edu.sandau.chat.service.UserService;
import edu.sandau.chat.vo.MyFriendsVO;
import edu.sandau.chat.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return userService.searchUser(name);
    }

    /***
     * 查询指定用户信息
     * @param userId
     * @return
     */
    @GetMapping("/queryUser/{userId}")
    public UserVO getUserVOById(@PathVariable("userId") Integer userId) {
        return userService.getUserVOById(userId);
    }

    /***
     * 查询好友列表
     * @return
     */
    @GetMapping("/friend")
    public List<MyFriendsVO> myFriends() {
        List<MyFriendsVO> friendsVOS = userService.queryMyFriends(RequestContent.getCurrentUser().getId());
        return friendsVOS;
    }

    /***
     * 查看用户信息
     * @return
     */
    @GetMapping("/info")
    public User getUserInfo() {
        int currentId = RequestContent.getCurrentUser().getId();
        return userService.getUserInfo(currentId);
    }

    /***
     * 修改用户信息
     * @param user
     * @return
     */
    @PostMapping("/info")
    public User changeUserInfo(@RequestBody User user) {
        return userService.changeUserInfo(user);
    }

    /***
     * 查询群组列表
     * @return
     */
    @GetMapping("/group")
    public List<Group> myGroups() {
        return userService.queryMyGroups(RequestContent.getCurrentUser().getId());
    }

}
