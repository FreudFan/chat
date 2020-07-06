package edu.sandau.chat.service;

import edu.sandau.chat.enums.RequestFriendsStatusEnum;
import edu.sandau.chat.vo.UserVO;
import edu.sandau.chat.entity.user.FriendGroup;

import java.util.List;

public interface FriendService {
    /***
     * 同意好友申请
     * @param friendId
     * @return 好友基本信息
     */
    Integer agreeFriendRequest(Integer friendId);

    /***
     * 忽略好友请求
     * 直接删除请求表字段
     * @param sendUserId
     */
    void ignoreFriendRequest(Integer sendUserId);

    /***
     * 申请添加好友
     * 前置条件:
     * 1. 搜索的用户如果不存在，返回[没有找到用户]
     * 2. 搜索账号是你自己，返回[不能添加自己]
     * 3. 搜索的朋友已经是你的好友，返回[该用户已经是你的好友啦]
     * 4. 在五分钟内重复申请，返回[已经申请过啦...]
     * 5. 申请成功，返回[OK]
     * @param userId
     * @return
     */
    RequestFriendsStatusEnum requestFriend(Integer userId);

    /***
     * 通过好友id查询好友信息
     * @param friendId 好友id
     * @return
     */
    UserVO findFriendInfoById(Integer friendId);

    /***
     * 更新创建用户分组
     * @param friendGroup
     */
    boolean modifyFriendGroupName(FriendGroup friendGroup);

    /***
     * 删除用户分组,分组内必须没有用户
     * @param friendGroup
     */
    boolean deleteFriendGroup(FriendGroup friendGroup);

    /***
     * 好友通知（有哪些人向我发送了好友请求）
     * @param userId
     * @return
     */
    List<UserVO> listFriendRequest(Integer userId);

    /***
     * 删除好友
     * @param friendId
     * @return
     */
    boolean deleteFriend(Integer friendId);
}
