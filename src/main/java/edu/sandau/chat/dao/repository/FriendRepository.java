package edu.sandau.chat.dao.repository;

import edu.sandau.chat.entity.Friend;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FriendRepository extends CrudRepository<Friend, Integer> {

    List<Friend> findAllByUserId(Integer userId);

    Friend findAllByUserIdAndFriendId(Integer userId, Integer friendId);
}
