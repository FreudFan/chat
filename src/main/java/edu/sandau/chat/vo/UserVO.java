package edu.sandau.chat.vo;

import lombok.Data;

/***
 * 用户概要
 */
@Data
public class UserVO {
    private Integer id;
    private String name;
    private String nickname;
    private Integer gender;
    private String headPortrait;
    private Boolean isFriend;
}
