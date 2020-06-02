package edu.sandau.chat.vo;

import lombok.Data;

@Data
public class AcceptFriendVO {
    /***
     * 好友id
     */
    Integer friendId;
    /***
     * 对好友申请的操作
     * 枚举：OperatorFriendRequestTypeEnum
     */
    Integer operationType;
}
