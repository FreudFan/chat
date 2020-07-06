package edu.sandau.chat.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.sandau.chat.dao.GroupMessageDao;
import edu.sandau.chat.entity.group.GroupMessage;
import edu.sandau.chat.netty.ChatMsg;
import edu.sandau.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service("groupMessageService")
public class GroupMessageServiceImpl implements MessageService {
    @Autowired
    private GroupMessageDao groupMessageDao;

    @Override
    public Integer saveMessage(ChatMsg chatMsg) {
        GroupMessage message = new GroupMessage();
        message.setSenderUserId(chatMsg.getSenderId());
        message.setGroupId(chatMsg.getGroupId());
        message.setMessage(chatMsg.getMsg());
        return groupMessageDao.getRepository().save(message).getId();
    }

    @Override
    public void updateMsgSigned(List<Integer> msgIdList) {

    }

    @Override
    public List<ChatMsg> getUnReadMsgList(Integer acceptUserId) {
        return null;
    }

    @Override
    public PageInfo<GroupMessage> getHistoryGroupMessage(Integer groupId, Integer pageNum, Integer size, LocalDate startDate) {
        if (size == null) {
            size = 20;
        }
        PageHelper.startPage(pageNum, size);
        List<GroupMessage> groupMessages = groupMessageDao.getMapper().getHistoryMessage(groupId, startDate);
        return new PageInfo<>(groupMessages);
    }
}
