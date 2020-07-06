package edu.sandau.chat.dao.mapper;

import edu.sandau.chat.entity.group.GroupMessage;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GroupMessageMapper {

    /***
     * 查询历史聊天记录
     * @param groupId 群Id
     * @param startDate 起始查询时间
     * @return
     */
    List<GroupMessage> getHistoryMessage(Integer groupId, LocalDate startDate);

}
