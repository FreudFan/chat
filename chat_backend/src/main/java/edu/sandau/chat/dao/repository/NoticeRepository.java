package edu.sandau.chat.dao.repository;

import edu.sandau.chat.entity.group.Notice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NoticeRepository extends CrudRepository<Notice, Integer> {

    void deleteByGroupIdAndId(Integer groupId, Integer noticeId);

    List<Notice> findAllByGroupId(Integer groupId);

}
