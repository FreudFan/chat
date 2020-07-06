package edu.sandau.chat.dao.repository;

import edu.sandau.chat.entity.group.Group;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GroupRepository extends CrudRepository<Group, Integer> {

    List<Group> findAllById(Integer groupId);

}
