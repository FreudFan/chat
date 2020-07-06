package edu.sandau.chat.dao.mapper;

import edu.sandau.chat.vo.GroupRequestVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRequestMapper {

    /***
     * 查询加群申请列表
     * @param groupId
     * @return
     */
    List<GroupRequestVO> queryGroupRequest(Integer groupId);

}
