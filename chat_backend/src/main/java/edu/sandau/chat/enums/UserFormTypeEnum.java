package edu.sandau.chat.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/***
 * 用户登录方式
 */
@AllArgsConstructor
@Getter
public enum UserFormTypeEnum {
    /***
     * 邮箱
     */
    EMAIL(0, "email"),
    /***
     * 手机号
     */
    TELEPHONE(1, "telephone"),
    /***
     * 用户名
     */
    NAME(2, "name");

    public final Integer value;
    public final String name;

    public static UserFormTypeEnum getEnumByValue(Integer type) {
        for (UserFormTypeEnum actionEnum : UserFormTypeEnum.class.getEnumConstants()) {
            if (actionEnum.getValue().equals(type)) {
                return actionEnum;
            }
        }
        return null;
    }
}
