package edu.sandau.chat.dao;

import edu.sandau.chat.dao.repository.FriendRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Getter
public class FriendDao {
    @Autowired
    private FriendRepository repository;
}
