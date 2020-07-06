package edu.sandau.chat.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GroupRoleEnum {

    MEMBER(0, "群成员"),
    ADMIN(1, "管理员"),
    MASTER(2, "群主");

    public final Integer value;
    public final String name;
}
