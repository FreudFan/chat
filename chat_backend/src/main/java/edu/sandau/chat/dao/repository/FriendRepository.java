package edu.sandau.chat.dao.repository;

import edu.sandau.chat.entity.user.Friend;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FriendRepository extends CrudRepository<Friend, Integer> {

    List<Friend> findAllByUserId(Integer userId);

    Friend findByUserIdAndFriendId(Integer userId, Integer friendId);
}
