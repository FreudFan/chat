package edu.sandau.chat.dao.mapper;

import edu.sandau.chat.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
}
