package edu.sandau.chat.dao.mapper;

import edu.sandau.chat.entity.user.User;
import edu.sandau.chat.vo.UserVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    /***
     * 用户名/手机号/邮箱 登录
     * @param loginValue 登录方式
     * @param loginName 用户名/手机号/邮箱
     * @param password 密码
     * @return
     */
    User login(String loginValue, String loginName, String password);

    /***
     * 查看字段重复次数
     * @param column 属性
     * @param value 值
     * @return
     */
    int count(String column, String value);

    /***
     * 模糊搜索用户（不查询当前用户）
     * @param column 属性
     * @param value 值
     * @param currentId 当前用户id
     * @return
     */
    List<UserVO> searchUser(String column, String value, Integer currentId);

    /***
     * 查询好友列表
     * @param userId 用户id
     * @return 好友概要信息
     */
    List<UserVO> queryFriendsList(Integer userId);

    /***
     * 根据名字查询用户
     * @param username name值是唯一的
     * @return
     */
    User findByName(String username);

    /***
     * 根据用户id查询好友基本信息
     * @param userId 当前用户id
     * @param friendId 好友id
     * @return
     */
    UserVO findFriendById(Integer userId, Integer friendId);

    /***
     * 查询向我发送好友请求的用户
     * @param userId
     * @return
     */
    List<UserVO> findRequestUser(Integer userId);

    /***
     * 查询用户基本信息
     * @param userId
     * @return
     */
    UserVO findUserVOById(Integer userId);
}
