package edu.sandau.chat.netty;

import edu.sandau.chat.service.GroupService;
import edu.sandau.chat.utils.JacksonUtil;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("channelUtils")
public class ChannelUtils {
    @Autowired
    private GroupService groupService;

    /***
     * 向单个用户发送通知
     * @param senderId 发勇用户id (0是系统)
     * @param targetId 目标用户(群)id
     * @param dataContent 消息内容
     */
    public void sendMessageToUser(Integer senderId, Integer targetId, DataContent dataContent) {
        Channel receiverChannel = UserChannelRel.get(targetId);
        if (receiverChannel == null) {
            // 用户离线消息
            this.sendOfflineMessage();
        } else {
            Channel findChannel = ChatHandler.users.find(receiverChannel.id());
            if (findChannel != null) {
                receiverChannel.writeAndFlush(new TextWebSocketFrame(JacksonUtil.toJSON(dataContent)));
            } else {
                // 用户离线消息
                this.sendOfflineMessage();
            }
        }
    }

    /***
     * 广播群通知
     * @param senderId
     * @param groupId
     * @param dataContent
     */
    public void sendMessageToGroupUser(Integer senderId, Integer groupId, DataContent dataContent) {
        List<Integer> userIds = groupService.getGroupUsersId(groupId);
        ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        userIds.forEach(id -> {
            Channel channel = UserChannelRel.get(id);
            if (channel != null) {
                group.add(channel);
            }
        });
        group.writeAndFlush(new TextWebSocketFrame(JacksonUtil.toJSON(dataContent)));
    }

    /***
     * 发送离线消息
     */
    public void sendOfflineMessage() {

    }

}
