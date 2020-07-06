package edu.sandau.chat.dao.repository;

import edu.sandau.chat.entity.group.GroupUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GroupUserRepository extends CrudRepository<GroupUser, Integer> {

    void deleteByGroupIdAndUserId(Integer groupId, Integer userId);

    List<GroupUser> findAllByUserId(Integer userId);

    GroupUser findByGroupIdAndUserId(Integer groupId, Integer userId);
}
