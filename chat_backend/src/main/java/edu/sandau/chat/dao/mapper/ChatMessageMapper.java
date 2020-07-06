package edu.sandau.chat.dao.mapper;

import edu.sandau.chat.entity.user.ChatMessage;
import edu.sandau.chat.netty.ChatMsg;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChatMessageMapper {

    /***
     * 批量签收消息
     * @param msgIdList
     */
    void batchUpdateMsgSigned(List<Integer> msgIdList);

    /***
     * 查询用户未签收的消息
     * @param userId
     * @return
     */
    List<ChatMsg> getUnReadMessage(Integer userId);

    /***
     * 查询历史聊天记录
     * @param currentId 当前用户Id
     * @param userId    目标用户Id
     * @param startDate 起始查询时间
     * @return
     */
    List<ChatMessage> getHistoryMessage(Integer currentId, Integer userId, LocalDate startDate);

}
