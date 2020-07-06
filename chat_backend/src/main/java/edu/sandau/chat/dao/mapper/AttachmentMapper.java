package edu.sandau.chat.dao.mapper;

import edu.sandau.chat.entity.Attachment;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentMapper {

    Attachment getAttachmentById(String id);

    Attachment getAttachmentInfoById(String id);
}
