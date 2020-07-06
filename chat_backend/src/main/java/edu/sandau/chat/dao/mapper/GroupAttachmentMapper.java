package edu.sandau.chat.dao.mapper;

import edu.sandau.chat.entity.group.GroupAttachment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupAttachmentMapper {

    List<GroupAttachment> findGroupAttachmentByGroupId(Integer groupId);

}
