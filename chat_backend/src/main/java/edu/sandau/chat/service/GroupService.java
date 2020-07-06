package edu.sandau.chat.service;

import edu.sandau.chat.entity.group.Group;
import edu.sandau.chat.vo.GroupRequestVO;
import edu.sandau.chat.vo.GroupVO;
import edu.sandau.chat.vo.UserVO;

import java.util.List;

import edu.sandau.chat.enums.RequestGroupStatusEnum;

public interface GroupService {

    /***
     * 新建群
     * @param group
     * @return
     */
    Group createGroup(Group group);

    /***
     * 申请加入群
     * 前置条件:
     * 0. 申请成功，返回[OK]
     * 1. 搜索的群如果不存在，返回[未找到群...]
     * 2. 搜索的群号已经进入，返回[你已在群中...]
     * 3. 在五分钟内重复申请，返回[已经申请过啦...]
     * @param GroupId
     * @return
     */
    RequestGroupStatusEnum requestGroup(Integer GroupId);

    /***
     * 同意用户加群申请
     * @param groupId
     * @return boolean
     */
    boolean agreeGroupRequest(Integer groupId, Integer sendUserId);

    /***
     * 忽略用户加群请求
     * 直接删除请求表字段
     * @param acceptGroupId
     * @param sendUserId
     */
    void ignoreGroupRequest(Integer acceptGroupId, Integer sendUserId);

    /***
     * 解散群 假删除
     * 禁用群
     * @param groupId
     */
    void disbandGroup(Integer groupId);

    /**
     * 查询群成员列表
     *
     * @param groupId
     * @return
     */
    List<UserVO> queryGroupUsers(Integer groupId);

    /***
     * 模糊搜索群
     * @param name 用户名
     * @return
     */
    List<GroupVO> searchGroup(String name);

    /***
     * 查询群资料
     * @param groupId
     * @return
     */
    Group getGroupInfo(Integer groupId);

    /***
     * 查询用户所属群的权限
     * @param groupId
     * @return
     */
    int getGroupUserRole(Integer groupId);

    /***
     * 编辑群资料
     * @param group
     * @return
     */
    Group changeGroupInfo(Group group);

    /***
     * 查找群用户id
     * @param groupId
     * @return
     */
    List<Integer> getGroupUsersId(Integer groupId);

    /***
     * 群主设置用户的管理员权限
     * @param groupId
     * @param setUserId
     * @return
     */
    boolean setGroupUserManageRole(Integer groupId, Integer setUserId);

    /***
     * 群主移除用户的管理员权限
     * @param groupId
     * @param setUserId
     * @return
     */
    boolean removeGroupUserManageRole(Integer groupId, Integer setUserId);

    /***
     * 移除群用户功能（群主能移除所有用户，管理员只能移除普通用户）
     * @param groupId
     * @param setUserId
     * @return
     */
    boolean removeGroupUser(Integer groupId, Integer setUserId);

    /***
     * 查询群列表中的某用户资料
     * @param groupId
     * @param userId
     * @return
     */
    UserVO showGroupUserInfo(Integer groupId, Integer userId);

    /***
     * 获取用户在群内昵称
     * @param groupId
     * @param userId
     * @return
     */
    String getUserGroupNickname(Integer groupId, Integer userId);


    /***
     * 群通知（只有群主和管理员看得到）
     * @param groupId
     * @return
     */
    List<GroupRequestVO> listGroupRequest(Integer groupId);

}
