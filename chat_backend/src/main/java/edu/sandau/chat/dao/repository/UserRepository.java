package edu.sandau.chat.dao.repository;

import edu.sandau.chat.entity.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
