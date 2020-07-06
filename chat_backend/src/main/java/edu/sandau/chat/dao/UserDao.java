package edu.sandau.chat.dao;

import edu.sandau.chat.dao.mapper.UserMapper;
import edu.sandau.chat.dao.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Getter
public class UserDao {
    @Autowired
    private UserMapper mapper;
    @Autowired
    private UserRepository repository;
}
