package edu.sandau.chat.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/***
 * 用户登录方式
 */
@Getter
@AllArgsConstructor
public enum LoginValueEnum {
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
    USERNAME(2, "name");

    private final Integer value;
    private final String name;
}
