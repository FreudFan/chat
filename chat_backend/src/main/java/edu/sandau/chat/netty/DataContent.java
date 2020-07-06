package edu.sandau.chat.netty;

import lombok.Data;

import java.io.Serializable;

@Data
public class DataContent implements Serializable {
    /** 动作类型 */
    private Integer action;
    /** 消息内容实体 */
    private ChatMsg chatMsg;
    /** 扩展字段 */
    private String extend;
}
