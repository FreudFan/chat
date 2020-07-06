package edu.sandau.chat.service.impl;

import edu.sandau.chat.dao.GroupAttachmentDao;
import edu.sandau.chat.dao.GroupUserDao;
import edu.sandau.chat.entity.Attachment;
import edu.sandau.chat.entity.group.GroupAttachment;
import edu.sandau.chat.entity.group.GroupUser;
import edu.sandau.chat.enums.GroupRoleEnum;
import edu.sandau.chat.interceptor.RequestContent;
import edu.sandau.chat.service.AttachmentService;
import edu.sandau.chat.service.GroupAttachmentService;
import edu.sandau.chat.vo.GroupAttachmentVO;
import edu.sandau.chat.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class GroupAttachmentServiceImpl implements GroupAttachmentService {

    @Autowired
    private GroupAttachmentDao groupAttachmentDao;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private GroupUserDao groupuserDao;

    @Override
    public Integer uploadGroupAttachment(Integer groupId, MultipartFile file) {
        GroupAttachment groupAttachment = new GroupAttachment();
        try {
            String fileId = attachmentService.saveAttachment(file);
            groupAttachment.setFileId(fileId);
            groupAttachment.setGroupId(groupId);
            groupAttachmentDao.getRepository().save(groupAttachment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupAttachment.getId();
    }

    @Override
    public List<GroupAttachmentVO> showGroupAttachmentList(Integer groupId) {
        List<GroupAttachmentVO> groupAttachmentVOS = new ArrayList<>();
        List<GroupAttachment> groupAttachments = groupAttachmentDao.getMapper().findGroupAttachmentByGroupId(groupId);
        for (GroupAttachment groupAttachment : groupAttachments) {
            GroupAttachmentVO groupAttachmentVO = new GroupAttachmentVO();
            Attachment attachment = attachmentService.getAttachmentInfo(groupAttachment.getFileId());
            groupAttachmentVO.setContentSize(attachment.getContentSize());
            groupAttachmentVO.setFileName(attachment.getName());
            groupAttachmentVO.setFileId(groupAttachment.getFileId());
            Optional<GroupUser> user = groupuserDao.getRepository().findById(attachment.getOwnerId());
            UserVO userVO = new UserVO();
            userVO.setId(user.get().getUserId());
            userVO.setNickname(user.get().getNickname());
            groupAttachmentVO.setUserVO(userVO);
            groupAttachmentVOS.add(groupAttachmentVO);
        }
        return groupAttachmentVOS;
    }

    @Override
    public boolean deleteGroupAttachment(Integer groupId, String fileId) {
        int currentId = RequestContent.getCurrentUser().getId();
        GroupUser groupUser = groupuserDao.getRepository().findByGroupIdAndUserId(groupId, currentId);
        if (groupUser.getRoleId().equals(GroupRoleEnum.MEMBER.value))
            return false;
        groupAttachmentDao.getRepository().deleteByGroupIdAndFileId(groupId, fileId);
        attachmentService.deleteAttachment(fileId);
        return true;
    }
}
