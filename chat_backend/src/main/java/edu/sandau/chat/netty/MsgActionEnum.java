package edu.sandau.chat.netty;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 发送消息的动作 枚举
 */
@AllArgsConstructor
@Getter
public enum MsgActionEnum {

    CONNECT(1, "第一次(或重连)初始化连接"),
    CHAT(2, "聊天消息"),
    SIGNED(3, "消息签收"),
    KEEPALIVE(4, "客户端保持心跳"),
    PULL_FRIEND(5, "拉取好友"),
    NEW_FRIEND(6, "好友申请通过"),
    NEW_GROUP_MEMBER(7, "新成员加群"),
    DISBAND_GROUP(8, "解散群"),
    REQUEST_FRIEND(9, "通知有好友发起申请"),
    GROUP_CHAT(10, "群聊天消息"),
    GROUP_NOTICE(11, "群公告");

    public final Integer type;
    public final String value;

    public static MsgActionEnum getEnumByType(Integer type) {
        for (MsgActionEnum actionEnum : MsgActionEnum.class.getEnumConstants()) {
            if (actionEnum.getType().equals(type)) {
                return actionEnum;
            }
        }
        return null;
    }

}
