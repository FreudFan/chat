package edu.sandau.chat.service;

import com.github.pagehelper.PageInfo;
import edu.sandau.chat.entity.group.GroupMessage;
import edu.sandau.chat.entity.user.ChatMessage;
import edu.sandau.chat.netty.ChatMsg;

import java.time.LocalDate;
import java.util.List;

public interface MessageService {

    /***
     * 保存消息
     * @param chatMsg
     * @return
     */
    Integer saveMessage(ChatMsg chatMsg);

    /***
     * 批量签收消息
     * @param msgIdList
     */
    void updateMsgSigned(List<Integer> msgIdList);

    /***
     * 读取用户未签收的消息
     * @param acceptUserId 接受用户id
     * @return
     */
    List<ChatMsg> getUnReadMsgList(Integer acceptUserId);

    /***
     * 分页查询用户历史消息
     * @param userId 用户id
     * @param pageNum 页码
     * @param size 单页记录
     * @param startDate 起始时间
     * @return
     */
    default PageInfo<ChatMessage> getHistoryChatMessage(Integer userId, Integer pageNum, Integer size, LocalDate startDate){
        return null;
    }

    /***
     * 分页查询历史记录
     * @param groupId 群id
     * @param pageNum 页码
     * @param size 单页记录
     * @param startDate 起始时间
     * @return
     */
    default PageInfo<GroupMessage> getHistoryGroupMessage(Integer groupId, Integer pageNum, Integer size, LocalDate startDate){
        return null;
    }

}
