package edu.sandau.chat.dao.repository;

import edu.sandau.chat.entity.FriendGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FriendGroupRepository extends CrudRepository<FriendGroup, Integer> {

    /***
     * 根据用户id查询分组名称
     * @param userId
     * @return
     */
    List<FriendGroup> findAllByUserId(Integer userId);
}
