package edu.sandau.chat.dao.repository;

import edu.sandau.chat.entity.user.ChatMessage;
import org.springframework.data.repository.CrudRepository;

public interface ChatMessageRepository extends CrudRepository<ChatMessage, Integer> {
}
