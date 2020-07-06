package edu.sandau.chat.dao;

import edu.sandau.chat.dao.mapper.GroupAttachmentMapper;
import edu.sandau.chat.dao.repository.GroupAttachmentRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Getter
public class GroupAttachmentDao {
    @Autowired
    private GroupAttachmentRepository repository;
    @Autowired
    private GroupAttachmentMapper mapper;

}
