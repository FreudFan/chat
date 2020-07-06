package edu.sandau.chat.dao.repository;

import edu.sandau.chat.entity.Attachment;
import org.springframework.data.repository.CrudRepository;

public interface AttachmentRepository extends CrudRepository<Attachment, Integer> {
}
