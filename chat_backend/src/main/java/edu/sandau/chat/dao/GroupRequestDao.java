package edu.sandau.chat.dao;

import edu.sandau.chat.dao.mapper.GroupRequestMapper;
import edu.sandau.chat.dao.repository.GroupRequestRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Getter
public class GroupRequestDao {
    @Autowired
    private GroupRequestRepository repository;
    @Autowired
    private GroupRequestMapper mapper;
}
