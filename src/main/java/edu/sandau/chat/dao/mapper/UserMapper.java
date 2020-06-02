package edu.sandau.chat.dao.mapper;

import edu.sandau.chat.entity.User;
import edu.sandau.chat.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
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
}
