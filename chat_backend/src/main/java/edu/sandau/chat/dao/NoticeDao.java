package edu.sandau.chat.dao;

import edu.sandau.chat.dao.repository.NoticeRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Getter
public class NoticeDao {
    @Autowired
    private NoticeRepository repository;

}
