package edu.sandau.chat.dao;

import edu.sandau.chat.dao.mapper.GroupMapper;
import edu.sandau.chat.dao.repository.GroupRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Getter
public class GroupDao {
    @Autowired
    private GroupRepository repository;
    @Autowired
    private GroupMapper mapper;
}
