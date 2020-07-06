package edu.sandau.chat.dao;

import edu.sandau.chat.dao.mapper.AttachmentMapper;
import edu.sandau.chat.dao.repository.AttachmentRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Getter
public class AttachmentDao {
    @Autowired
    private AttachmentRepository repository;
    @Autowired
    private AttachmentMapper mapper;
}
