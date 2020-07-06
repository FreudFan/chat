package edu.sandau.chat.service.impl;

import edu.sandau.chat.dao.GroupUserDao;
import edu.sandau.chat.dao.NoticeDao;
import edu.sandau.chat.entity.group.Notice;
import edu.sandau.chat.interceptor.RequestContent;
import edu.sandau.chat.netty.ChannelUtils;
import edu.sandau.chat.netty.ChatMsg;
import edu.sandau.chat.netty.DataContent;
import edu.sandau.chat.netty.MsgActionEnum;
import edu.sandau.chat.service.GroupNoticeService;
import edu.sandau.chat.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class GroupNoticeServiceImpl implements GroupNoticeService {
    @Autowired
    private GroupUserDao groupuserDao;
    @Autowired
    private NoticeDao noticeDao;
    @Autowired
    private ChannelUtils channelUtils;

    @Override
    public boolean addNotice(Notice notice) {
        int currentId = RequestContent.getCurrentUser().getId();
        if (groupuserDao.getRepository().findByGroupIdAndUserId(notice.getGroupId(), currentId).getRoleId() == 0) {
            return false;
        }
        Notice groupNotice = new Notice();
        groupNotice.setTitle(notice.getTitle());
        groupNotice.setContent(notice.getContent());
        groupNotice.setGroupId(notice.getGroupId());
        groupNotice.setOwnerId(currentId);
        notice = noticeDao.getRepository().save(groupNotice);
        this.sendNotice(notice.getGroupId(), notice);
        return true;
    }

    @Override
    public boolean deleteNotice(Integer groupId, Integer noticeId) {
        int currentId = RequestContent.getCurrentUser().getId();
//        if (groupuserDao.getRepository().findByGroupIdAndUserId(noticeId, currentId).getRoleId() == 0) {
//            return false;
//        }
        noticeDao.getRepository().deleteById(noticeId);
        return true;
    }

    @Override
    public boolean updateNotice(Notice notice) {
        int currentId = RequestContent.getCurrentUser().getId();
        Notice getNotice = noticeDao.getRepository().findById(notice.getId()).get();
        if (groupuserDao.getRepository().findByGroupIdAndUserId(getNotice.getGroupId(), currentId).getRoleId() == 0) {
            return false;
        }
        notice.setOwnerId(getNotice.getOwnerId());
        notice.setCreateTime(getNotice.getCreateTime());
        notice.setGroupId(getNotice.getGroupId());
        notice = noticeDao.getRepository().save(notice);
        this.sendNotice(notice.getGroupId(), notice);
        return true;
    }

    @Override
    public Notice showNotice(Integer noticeId) {
        return noticeDao.getRepository().findById(noticeId).get();
    }

    @Override
    public List<Notice> showNoticeList(Integer groupId) {
        return noticeDao.getRepository().findAllByGroupId(groupId);
    }

    /***
     * 群发群公告
     * @param groupId
     * @param notice
     */
    private void sendNotice(Integer groupId, Notice notice) {
        DataContent dataContent = new DataContent();
        dataContent.setAction(MsgActionEnum.GROUP_NOTICE.type);
        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setGroupId(groupId);
        dataContent.setExtend(JacksonUtil.toJSON(notice));
        int currentId = RequestContent.getCurrentUser().getId();
        channelUtils.sendMessageToGroupUser(currentId, groupId, dataContent);
    }
}
