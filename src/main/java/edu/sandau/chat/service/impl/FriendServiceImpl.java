package edu.sandau.chat.service.impl;

import edu.sandau.chat.dao.FriendDao;
import edu.sandau.chat.dao.FriendGroupDao;
import edu.sandau.chat.entity.Friend;
import edu.sandau.chat.entity.FriendGroup;
import edu.sandau.chat.enums.UserFormTypeEnum;
import edu.sandau.chat.interceptor.RequestContent;
import edu.sandau.chat.service.FriendService;
import edu.sandau.chat.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendDao friendDao;
    private FriendGroupDao friendGroupDao;

    @Override
    public void addFriendToList(Integer friendId) {
        Integer currentId = RequestContent.getCurrentUser().getId();
        Friend friend = new Friend();
        friend.setUserId(currentId);
        friend.setFriendId(friendId);
        friendDao.getRepository().save(friend);
    }
/*
    @Override
    public boolean modifyFriendGroup(FriendGroup friendGroup, String newFriendGroupName) {
        if(friendGroup.toString().equals(null))
            FriendGroup friendGroup1 = new FriendGroup();
        friendGroup.setName(newFriendGroupName);
        friendGroupDao.getRepository().save(friendGroup);
        return true;
    }
*/

    @Override
    public boolean modifyFriendGroupName(FriendGroup friendGroup) {
        int currentId = RequestContent.getCurrentUser().getId();
        if(friendGroup.getId().equals(null))
            friendGroup.setUserId(currentId);
        friendGroupDao.getRepository().save(friendGroup);
        return true;
    }



    @Override
    public boolean deleteFriendGroup(FriendGroup friendGroup) {
        int currentId = RequestContent.getCurrentUser().getId();
        List<Friend> friends = friendDao.getRepository().findAllByUserId(currentId);
        for(int i=0;i<friends.size();i++)
        {
            if(friendGroup.getId().equals(friends.get(i).getGroupId()))
                return false;
        }
        return true;
    }
}
