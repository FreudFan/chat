package edu.sandau.chat.dao;

import edu.sandau.chat.dao.mapper.ChatMessageMapper;
import edu.sandau.chat.dao.repository.ChatMessageRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Getter
public class ChatMessageDao {
    @Autowired
    private ChatMessageRepository repository;
    @Autowired
    private ChatMessageMapper mapper;
}
