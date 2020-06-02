package edu.sandau.chat.dao.repository;

import edu.sandau.chat.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findAllByName(String username);
}
