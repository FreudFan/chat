package edu.sandau.chat.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息签收状态 枚举
 */
@AllArgsConstructor
@Getter
public enum MsgSignFlagEnum {

    UNSIGNED(0, "未签收"),
    SIGNED(1, "已签收");

    public final Integer type;
    public final String content;

}
