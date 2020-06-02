package edu.sandau.chat.service;

public interface FriendService {
    /***
     * 把同意添加的用户加入自己的好友列表
     * @param friendId
     */
    void addFriendToList(Integer friendId);

}
