package edu.sandau.chat.utils;

import edu.sandau.chat.enums.UserFormTypeEnum;
import edu.sandau.chat.exception.FormException;
import org.apache.commons.lang3.math.NumberUtils;

public class FormCheckUtil {

    public static UserFormTypeEnum checkInputType(String text) {
        if (text.contains("@")) {
            //识别是否是邮箱
            return UserFormTypeEnum.EMAIL;
        } else if (text.length() == 11 && NumberUtils.isDigits(text)) {
            //识别是手机号
            return UserFormTypeEnum.TELEPHONE;
        } else {
            throw new FormException("文本框输入格式错误");
        }
    }
}
