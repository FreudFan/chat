package edu.sandau.chat.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 忽略或者通过 好友请求的枚举
 */
@Getter
@AllArgsConstructor
public enum OperatorFriendRequestTypeEnum {
	
	IGNORE(0, "忽略"),
	PASS(1, "通过");

	private final Integer value;
	private final String name;
	
}
