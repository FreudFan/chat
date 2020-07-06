package edu.sandau.chat.netty;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatMsg implements Serializable {
    /** 发送者：用户id */
    private Integer senderId;
    /** 接受者：用户id */
    private Integer receiverId;
    /** 接受者：群组id */
    private Integer groupId;
    /** 接受者：用户群内昵称id */
    private String nickname;
    /** 接受者：用户的用户名 */
    private String name;
    /** 消息内容 */
    private String msg;
    /** 用于消息的签收 */
    private String msgId;
}
