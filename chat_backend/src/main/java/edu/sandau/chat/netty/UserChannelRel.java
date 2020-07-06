package edu.sandau.chat.netty;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/***
 * 用户id和channel的关联关系处理
 */
@Slf4j
public class UserChannelRel {

    private static volatile HashMap<Integer, Channel> manager = new HashMap<>();

    public static void put(Integer senderId, Channel channel) {
        manager.put(senderId, channel);
    }

    public static Channel get(Integer senderId) {
        return manager.get(senderId);
    }

    public static void outPut() {
        for (HashMap.Entry<Integer, Channel> entry : manager.entrySet()) {
            log.info("用户[id: {}] 已绑定至channel [{}]", entry.getKey(), entry.getValue().id().asLongText());
        }
    }
}
