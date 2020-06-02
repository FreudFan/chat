package edu.sandau.chat.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 添加好友前置状态
 */
@AllArgsConstructor
public enum RequestFriendsStatusEnum {
	
	SUCCESS(0, "OK"),
	USER_NOT_EXIST(1, "无此用户..."),	
	NOT_YOURSELF(2, "不能添加你自己..."),			
	ALREADY_FRIENDS(3, "该用户已经是你的好友...");

	public final Integer value;
	public final String name;
	
}
