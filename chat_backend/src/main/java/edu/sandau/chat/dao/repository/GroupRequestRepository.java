package edu.sandau.chat.dao.repository;

import edu.sandau.chat.entity.group.GroupRequest;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.Id;
import java.util.List;

public interface GroupRequestRepository extends CrudRepository<GroupRequest, Id> {

    GroupRequest findBySendUserIdAndGroupId(Integer sendId, Integer acceptId);

    List<GroupRequest> findAllByGroupId(Integer groupId);

    void deleteByGroupIdAndAndSendUserId(Integer groupId, Integer userId);
}
