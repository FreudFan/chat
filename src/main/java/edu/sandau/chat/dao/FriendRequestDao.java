package edu.sandau.chat.dao;

import edu.sandau.chat.dao.repository.FriendRequestRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Getter
public class FriendRequestDao {
    @Autowired
    private FriendRequestRepository repository;
}
