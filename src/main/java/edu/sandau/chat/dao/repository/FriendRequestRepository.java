package edu.sandau.chat.dao.repository;

import edu.sandau.chat.entity.FriendRequest;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.Id;
import java.util.List;

public interface FriendRequestRepository extends CrudRepository<FriendRequest, Id> {

    List<FriendRequest> findAllByAcceptUserId(Integer acceptUserId);
}
