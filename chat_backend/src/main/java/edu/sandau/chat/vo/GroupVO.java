package edu.sandau.chat.vo;

import lombok.Data;

/***
 * 群概要
 */
@Data
public class GroupVO {
    private Integer groupId;
    private String name;
    private Boolean joined; //是否已加入群
}
