package edu.sandau.chat.service;

import edu.sandau.chat.entity.FriendGroup;
import edu.sandau.chat.entity.User;

import java.util.List;

public interface FriendService {
    /***
     * 把同意添加的用户加入自己的好友列表
     * @param friendId
     */
    void addFriendToList(Integer friendId);

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


}
