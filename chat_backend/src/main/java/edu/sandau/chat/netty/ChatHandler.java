package edu.sandau.chat.netty;

import edu.sandau.chat.boot.ApplicationContextUtil;
import edu.sandau.chat.service.GroupService;
import edu.sandau.chat.service.MessageService;
import edu.sandau.chat.service.UserService;
import edu.sandau.chat.utils.JacksonUtil;
import edu.sandau.chat.utils.RedisUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/***
 * 处理消息的 handler
 * TextWebSocketFrame：在netty中，用于为websocket专门处理文本的对象
 */
@Slf4j
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private MessageService chatMessageService =
            (MessageService) ApplicationContextUtil.getBean("chatMessageService");
    private MessageService groupMessageService =
            (MessageService) ApplicationContextUtil.getBean("groupMessageService");
    private ChannelUtils channelUtils =
            (ChannelUtils) ApplicationContextUtil.getBean("channelUtils");
    private GroupService groupService =
            (GroupService) ApplicationContextUtil.getBean("groupServiceImpl");
    private RedisUtil redisUtil =
            (RedisUtil) ApplicationContextUtil.getBean("redisUtil");
    private UserService userService =
            (UserService) ApplicationContextUtil.getBean("userServiceImpl");

    /***
     * 用于记录和管理所有客户端的channel
     */
    public static volatile ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String content = msg.text();
        log.info("收到来自频道 {} 的消息: {}", ctx.channel().id(), content);
        Channel currentChannel = ctx.channel();
        DataContent dataContent = JacksonUtil.fromJSON(content, DataContent.class);
        Integer action = dataContent.getAction();
        Integer senderId = dataContent.getChatMsg().getSenderId();
        // 判断用户是否已经登录
        if (!redisUtil.checkUser(senderId)) {
            throw new SocketException(SocketException.NOT_LOGIN);
        }
        MsgActionEnum actionEnum = MsgActionEnum.getEnumByType(action);
        assert actionEnum != null;
        switch (actionEnum) {
            case CONNECT:
                // 2.1 当 websocket 第一次 open 的时候，初始化channel，把 channel 和用户的唯一id关联
                UserChannelRel.put(senderId, currentChannel);
                // 测试
                for (Channel c : users) {
                    System.out.println(c.id().asLongText());
                }
                UserChannelRel.outPut();
                break;
            case CHAT:
                // 2.2 聊天类型的消息，把聊天记录保存到数据库，同时标记消息的签收状态[未签收]
                ChatMsg chatMsg = dataContent.getChatMsg();
                // 保存消息到数据库，并且标记为 未签收
                Integer msgId = chatMessageService.saveMessage(chatMsg);
                // 发送消息
                channelUtils.sendMessageToUser(chatMsg.getSenderId(), chatMsg.getReceiverId(), dataContent);
                break;
            case SIGNED:
                // 2.3 签收消息类型，针对具体的消息进行签收，修改数据库中对应消息的签收状态[已签收]
                // 扩展字段在signed类型的消息中，代表需要去签收的消息id，逗号间隔
                String msgIdStr = dataContent.getChatMsg().getMsgId();
                String[] msgIds = msgIdStr.split(",");
                List<Integer> msgIdList = Arrays.stream(msgIds).filter(StringUtils::isNoneBlank)
                        .map(Integer::parseInt).collect(Collectors.toList());
                if (!msgIdList.isEmpty()) {
                    //批量签收
                    chatMessageService.updateMsgSigned(msgIdList);
                }
                break;
            case GROUP_CHAT:
                // 群聊天
                ChatMsg groupMsg = dataContent.getChatMsg();
                Integer groupMsgId = groupMessageService.saveMessage(groupMsg);
                String groupNickname = groupService.getUserGroupNickname(groupMsg.getGroupId(), senderId);
                groupMsg.setNickname(groupNickname);
//                String name = userService.getUserInfo(senderId).getName();
//                groupMsg.setName(name);
                dataContent.setChatMsg(groupMsg);
                channelUtils.sendMessageToGroupUser(senderId, groupMsg.getGroupId(), dataContent);
                break;
            case KEEPALIVE:
                // 2.4 心跳类型的消息
                System.out.println("收到来自channel为[" + currentChannel + "]的心跳包...");
                break;
            default:
        }

    }

    /***
     * 当客户端连接服务器之后（打开连接）
     * 获取客户端的channel，并且放到channelGroup中去进行管理
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        users.add(ctx.channel());
        log.info("频道id: {} 已加入", ctx.channel().id());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 当触发handlerRemoved, ChannelGroup会自动移除对应客户端的channel
        users.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 发生异常之后关闭连接（关闭channel），随后从 channelGroup 中移除
        ctx.channel().close();
        users.remove(ctx.channel());
    }

}
