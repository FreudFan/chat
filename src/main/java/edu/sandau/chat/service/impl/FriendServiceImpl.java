package edu.sandau.chat.service.impl;

import edu.sandau.chat.dao.FriendDao;
import edu.sandau.chat.dao.FriendGroupDao;
import edu.sandau.chat.dao.FriendRequestDao;
import edu.sandau.chat.dao.UserDao;
import edu.sandau.chat.entity.Friend;
import edu.sandau.chat.entity.FriendGroup;
import edu.sandau.chat.entity.FriendRequest;
import edu.sandau.chat.entity.User;
import edu.sandau.chat.enums.RequestFriendsStatusEnum;
import edu.sandau.chat.interceptor.RequestContent;
import edu.sandau.chat.service.FriendService;
import edu.sandau.chat.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendDao friendDao;
    @Autowired
    private FriendGroupDao friendGroupDao;
    @Autowired
    private FriendRequestDao friendRequestDao;
    @Autowired
    private UserDao userDao;

    @Override
    public UserVO agreeFriendRequest(Integer friendId) {
        User myFriend = userDao.getRepository().findById(friendId).get();
        Integer currentId = RequestContent.getCurrentUser().getId();

        Friend friend = new Friend();
        friend.setUserId(currentId);
        friend.setFriendId(friendId);
        //默认昵称是他的名字
        friend.setNickname(myFriend.getName());
        //添加至好友表
        friendDao.getRepository().save(friend);
        //从好友申请表删除记录
        friendRequestDao.getRepository().deleteBySendUserIdAndAcceptUserId(friendId, currentId);
        //TODO: 通知申请人好友申请通过
        return this.findFriendInfoById(friendId);
    }

    @Override
    public void ignoreFriendRequest(Integer sendUserId) {
        Integer currentId = RequestContent.getCurrentUser().getId();
        friendRequestDao.getRepository().deleteBySendUserIdAndAcceptUserId(sendUserId, currentId);
    }

    @Override
    public RequestFriendsStatusEnum requestFriend(Integer userId) {
        User currentUser = RequestContent.getCurrentUser();
        Optional<User> userOptional = userDao.getRepository().findById(userId);
        if(!userOptional.isPresent()) {
            return RequestFriendsStatusEnum.USER_NOT_EXIST;
        }
        User acceptUser = userOptional.get();
        if(acceptUser.getName().equals(currentUser.getName())) {
            return RequestFriendsStatusEnum.NOT_YOURSELF;
        }
        Friend friend = friendDao.getRepository().findAllByUserIdAndFriendId(currentUser.getId(), acceptUser.getId());
        if(friend != null) {
            return RequestFriendsStatusEnum.ALREADY_FRIENDS;
        }

        FriendRequest friendRequest = friendRequestDao.getRepository()
                .findBySendUserIdAndAcceptUserId(currentUser.getId(), acceptUser.getId());
        //之前没有发送过申请
        if(friendRequest == null) {
            this.saveFriendRequest(acceptUser.getId());
        } else {
            LocalDateTime requestTime = friendRequest.getRequestDateTime();
            Duration duration = Duration.between(requestTime, LocalDateTime.now());
            long minus = duration.toMinutes();
            // 五分钟内不允许重复申请
            if(minus <= 5) {
                return RequestFriendsStatusEnum.TOO_FAST;
            } else {
                friendRequestDao.getRepository().delete(friendRequest);
                this.saveFriendRequest(acceptUser.getId());
            }
        }
        return RequestFriendsStatusEnum.SUCCESS;
    }

    /***
     * 保存申请记录，并提醒目标用户
     * @param acceptUserId
     */
    public void saveFriendRequest(Integer acceptUserId) {
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSendUserId(RequestContent.getCurrentUser().getId());
        friendRequest.setAcceptUserId(acceptUserId);
        friendRequestDao.getRepository().save(friendRequest);
        //TODO: 向目标用户发送好友申请
    }

    @Override
    public UserVO findFriendInfoById(Integer friendId) {
        int currentId = RequestContent.getCurrentUser().getId();
        return userDao.getMapper().findFriendById(currentId, friendId);
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
        if(friendGroup.getId() == null)
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

    @Override
    public List<UserVO> listFriendRequest(Integer userId) {
        return null;
    }
}
