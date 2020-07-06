package edu.sandau.chat.dao;

import edu.sandau.chat.dao.repository.GroupUserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Getter
public class GroupUserDao {
    @Autowired
    private GroupUserRepository repository;
}
