package edu.sandau.chat.enums;

import lombok.AllArgsConstructor;

/**
 * 添加群的前置状态
 */
@AllArgsConstructor
public enum RequestGroupStatusEnum {

    SUCCESS(0, "OK"),
    GROUP_NOT_EXIST(1, "未找到群..."),
    ALREADY_GROUP(2, "你已在群中..."),
    TOO_FAST(3, "已经申请过啦...");

    public final Integer value;
    public final String name;

}
