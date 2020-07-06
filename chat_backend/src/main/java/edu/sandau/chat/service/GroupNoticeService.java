package edu.sandau.chat.service;

import edu.sandau.chat.entity.group.Notice;

import java.util.List;

public interface GroupNoticeService {

    /***
     * 增加群公告
     * @param notice
     * @return
     */
    boolean addNotice(Notice notice);

    /***
     * 删除群公告
     * @param groupId , noticeId
     */
    boolean deleteNotice(Integer groupId,Integer noticeId);

    /***
     * 修改群公告
     * @param notice
     */
    boolean updateNotice(Notice notice);

    /***
     * 查询某个群公告
     * @param noticeId
     */
    Notice showNotice(Integer noticeId);

    /**
     * 展示群公告列表
     * @param groupId
     * @return  GroupAttachmentVO
     */
    List<Notice> showNoticeList(Integer groupId);

}
