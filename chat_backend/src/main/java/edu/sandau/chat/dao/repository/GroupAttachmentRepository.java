package edu.sandau.chat.dao.repository;

import edu.sandau.chat.entity.group.GroupAttachment;
import org.springframework.data.repository.CrudRepository;

public interface GroupAttachmentRepository extends CrudRepository<GroupAttachment, Integer> {

    void deleteByGroupIdAndFileId(Integer groupId, String fileId);
}
