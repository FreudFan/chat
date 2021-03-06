package edu.sandau.chat.interceptor;

import edu.sandau.chat.entity.user.User;

/***
 * 请求内容
 */
public class RequestContent {
    /*** 用户 */
    private static final ThreadLocal<User> userHolder = new ThreadLocal<>();

    public static void add(User user) {
        userHolder.set(user);
    }

    public static User getCurrentUser() {
        return userHolder.get();
    }

    public static void remove() {
        userHolder.remove();
    }

}
