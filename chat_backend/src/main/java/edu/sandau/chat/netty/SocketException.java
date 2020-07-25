package edu.sandau.chat.netty;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SocketException extends RuntimeException {

    public SocketException(String message) {
        super(message);
        log.error(message, this);
    }

    public final static String WRONG_FORMAT = "输入格式不正确";

    public final static String NOT_LOGIN = "socket用户未登录";
}
