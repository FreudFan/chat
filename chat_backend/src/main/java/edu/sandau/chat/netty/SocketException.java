package edu.sandau.chat.netty;

public class SocketException extends RuntimeException {

    public SocketException(String message) {
        super(message);
    }

    public final static String WRONG_FORMAT = "输入格式不正确";

    public final static String NOT_LOGIN = "socket用户未登录";
}
