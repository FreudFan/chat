package edu.sandau.chat.vo;

import lombok.Data;

import java.time.LocalDateTime;

/***
 * 群概要
 */
@Data
public class GroupRequestVO {
    private Integer id;
    private Integer sendUserId;
    private Integer groupId;
    private UserVO userVo;
    private LocalDateTime requestDateTime;
}
