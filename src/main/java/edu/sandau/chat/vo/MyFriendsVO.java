package edu.sandau.chat.vo;

import lombok.Data;

import java.util.List;

@Data
public class MyFriendsVO {
    /*** 分组id **/
    private Integer groupId;
    /*** 分组名称 **/
    private String groupName;
    /*** 好友列表 **/
    private List<UserVO> users;
}
