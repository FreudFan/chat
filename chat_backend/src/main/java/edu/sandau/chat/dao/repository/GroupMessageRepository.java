package edu.sandau.chat.dao.repository;

import edu.sandau.chat.entity.group.GroupMessage;
import org.springframework.data.repository.CrudRepository;

public interface GroupMessageRepository extends CrudRepository<GroupMessage, Integer> {
}
