package edu.sandau.chat.vo;

import lombok.Data;

@Data
public class AcceptGroupVO {
    /***
     * 发送申请的群id
     */
    Integer sendGroupId;

    /***
     * 发送申请的用户id
     */
    Integer sendUserId;

    /***
     * 对群申请的操作
     * 枚举：OperatorGroupRequestTypeEnum
     */
    Integer operationType;
}
