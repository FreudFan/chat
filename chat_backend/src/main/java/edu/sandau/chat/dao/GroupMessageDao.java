package edu.sandau.chat.dao;

import edu.sandau.chat.dao.mapper.GroupMessageMapper;
import edu.sandau.chat.dao.repository.GroupMessageRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Getter
public class GroupMessageDao {
    @Autowired
    private GroupMessageRepository repository;
    @Autowired
    private GroupMessageMapper mapper;
}
