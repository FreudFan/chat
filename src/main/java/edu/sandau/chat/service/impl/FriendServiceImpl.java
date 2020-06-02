package edu.sandau.chat.service.impl;

import edu.sandau.chat.dao.FriendDao;
import edu.sandau.chat.entity.Friend;
import edu.sandau.chat.interceptor.RequestContent;
import edu.sandau.chat.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendDao friendDao;

    @Override
    public void addFriendToList(Integer friendId) {
        Integer currentId = RequestContent.getCurrentUser().getId();
        Friend friend = new Friend();
        friend.setUserId(currentId);
        friend.setFriendId(friendId);
        friendDao.getRepository().save(friend);
    }
}
