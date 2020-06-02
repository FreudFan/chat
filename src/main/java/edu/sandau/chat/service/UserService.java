package edu.sandau.chat.service;

import edu.sandau.chat.entity.User;
import edu.sandau.chat.enums.UserFormTypeEnum;
import edu.sandau.chat.vo.MyFriendsVO;
import edu.sandau.chat.vo.UserVO;

import java.util.List;

public interface UserService {

    /***
     * 若登录失败，返回null
     * @param loginValue
     * @param name
     * @param password
     * @return
     */
    User login(UserFormTypeEnum loginValue, String name, String password);

    /***
     * 用户注册
     * @param user
     * @return
     */
    User register(User user);

    /***
     * 查重
     * @param userFormTypeEnum
     * @param value
     * @return
     */
    boolean check(UserFormTypeEnum userFormTypeEnum, String value);

    /***
     * 模糊搜索用户
     * @param name 用户名
     * @return
     */
    List<UserVO> searchUser(String name);

    /***
     * 申请朋友的前置条件
     * @param username 目标用户名
     * @return
     */
    User preconditionRequestFriend(String username);

    /***
     * 查询用户好友列表
     * @param userId
     * @return
     */
    List<MyFriendsVO> queryMyFriends(Integer userId);

}
