package edu.sandau.chat.enums;

import lombok.AllArgsConstructor;

/**
 * 忽略或者通过 群请求的枚举
 */
@AllArgsConstructor
public enum OperatorGroupRequestTypeEnum {

    IGNORE(0, "忽略"),
    PASS(1, "通过");

    public final Integer value;
    public final String name;

}
