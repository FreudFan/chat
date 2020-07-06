package edu.sandau.chat.service.impl;

import edu.sandau.chat.dao.FriendDao;
import edu.sandau.chat.dao.FriendGroupDao;
import edu.sandau.chat.dao.FriendRequestDao;
import edu.sandau.chat.dao.UserDao;
import edu.sandau.chat.entity.user.Friend;
import edu.sandau.chat.entity.user.FriendGroup;
import edu.sandau.chat.entity.user.FriendRequest;
import edu.sandau.chat.entity.user.User;
import edu.sandau.chat.netty.MsgActionEnum;
import edu.sandau.chat.enums.RequestFriendsStatusEnum;
import edu.sandau.chat.exception.FriendException;
import edu.sandau.chat.interceptor.RequestContent;
import edu.sandau.chat.netty.ChannelUtils;
import edu.sandau.chat.netty.ChatMsg;
import edu.sandau.chat.netty.DataContent;
import edu.sandau.chat.service.FriendService;
import edu.sandau.chat.service.UserService;
import edu.sandau.chat.utils.JacksonUtil;
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
    @Autowired
    private ChannelUtils channelUtils;
    @Autowired
    private UserService userService;

    @Override
    public Integer agreeFriendRequest(Integer friendId) {
        User currentUser = RequestContent.getCurrentUser();
        UserVO userVo = userDao.getMapper().findFriendById(currentUser.getId(), friendId);
        if (userVo != null) {
            throw new FriendException("该用户已经是你的好友啦~");
        }
        FriendRequest friendRequest = friendRequestDao.getRepository().findBySendUserIdAndAcceptUserId(friendId, currentUser.getId());
        if (friendRequest == null) {
            throw new FriendException("该用户没有申请你为好友哦~");
        }
        User myFriend = userDao.getRepository().findById(friendId).get();

        Friend friend1 = new Friend();
        friend1.setUserId(currentUser.getId());
        friend1.setFriendId(friendId);
        //设置默认分组
        friend1.setGroupId(-1);
        //默认昵称是他的名字
        friend1.setNickname(myFriend.getName());
        //添加至自己的好友表
        friendDao.getRepository().save(friend1);

        //添加至自己的好友表
        Friend friend2 = new Friend();
        friend2.setUserId(friendId);
        friend2.setFriendId(currentUser.getId());
        friend2.setGroupId(-1);
        friend2.setNickname(currentUser.getName());
        friendDao.getRepository().save(friend2);

        //从好友申请表删除记录
        friendRequestDao.getRepository().deleteBySendUserIdAndAcceptUserId(friendId, currentUser.getId());
        friendRequestDao.getRepository().deleteBySendUserIdAndAcceptUserId(currentUser.getId(), friendId);

        //通知申请人好友申请通过
        DataContent dataContent = new DataContent();
        dataContent.setAction(MsgActionEnum.PULL_FRIEND.type);
        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setSenderId(currentUser.getId());
        chatMsg.setReceiverId(friendId);
        dataContent.setChatMsg(chatMsg);
        channelUtils.sendMessageToUser(currentUser.getId(), friendId, dataContent);

        return friendId;
    }

    //忽略请求
    @Override
    public void ignoreFriendRequest(Integer sendUserId) {
        Integer currentId = RequestContent.getCurrentUser().getId();
        friendRequestDao.getRepository().deleteBySendUserIdAndAcceptUserId(sendUserId, currentId);
    }

    @Override
    public RequestFriendsStatusEnum requestFriend(Integer userId) {
        User currentUser = RequestContent.getCurrentUser();
        Optional<User> userOptional = userDao.getRepository().findById(userId);
        if (!userOptional.isPresent()) {
            return RequestFriendsStatusEnum.USER_NOT_EXIST;
        }
        User acceptUser = userOptional.get();
        if (acceptUser.getName().equals(currentUser.getName())) {
            return RequestFriendsStatusEnum.NOT_YOURSELF;
        }
        Friend friend = friendDao.getRepository().findByUserIdAndFriendId(currentUser.getId(), acceptUser.getId());
        if (friend != null) {
            return RequestFriendsStatusEnum.ALREADY_FRIENDS;
        }

        FriendRequest friendRequest = friendRequestDao.getRepository()
                .findBySendUserIdAndAcceptUserId(currentUser.getId(), acceptUser.getId());
        //之前没有发送过申请
        if (friendRequest == null) {
            this.saveFriendRequest(acceptUser.getId());
        } else {
            LocalDateTime requestTime = friendRequest.getRequestDateTime();
            Duration duration = Duration.between(requestTime, LocalDateTime.now());
            long minus = duration.toMinutes();
            // 五分钟内不允许重复申请
            if (minus <= 5) {
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
        Integer currentId = RequestContent.getCurrentUser().getId();
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSendUserId(currentId);
        friendRequest.setAcceptUserId(acceptUserId);
        friendRequestDao.getRepository().save(friendRequest);
        // 向目标用户发送好友申请
        UserVO userVO = userService.getUserVOById(acceptUserId);
        DataContent dataContent = new DataContent();
        dataContent.setAction(MsgActionEnum.REQUEST_FRIEND.type);
        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setSenderId(currentId);
        chatMsg.setReceiverId(acceptUserId);
        dataContent.setChatMsg(chatMsg);
        dataContent.setExtend(JacksonUtil.toJSON(userVO));
        channelUtils.sendMessageToUser(currentId, acceptUserId, dataContent);
    }

    @Override
    public UserVO findFriendInfoById(Integer friendId) {
        int currentId = RequestContent.getCurrentUser().getId();
        UserVO userVo = userDao.getMapper().findFriendById(currentId, friendId);
        if (userVo == null) {
            throw new FriendException("该用户不是你的好友哦~");
        }
        return userVo;
    }

    @Override
    public boolean modifyFriendGroupName(FriendGroup friendGroup) {
        int currentId = RequestContent.getCurrentUser().getId();
        if (friendGroup.getId() == null)
            friendGroup.setUserId(currentId);
        friendGroupDao.getRepository().save(friendGroup);
        return true;
    }

    @Override
    public boolean deleteFriendGroup(FriendGroup friendGroup) {
        int currentId = RequestContent.getCurrentUser().getId();
        List<Friend> friends = friendDao.getRepository().findAllByUserId(currentId);
        for (int i = 0; i < friends.size(); i++) {
            if (friendGroup.getId().equals(friends.get(i).getGroupId()))
                return false;
        }
        friendGroupDao.getRepository().deleteById(friendGroup.getId());
        return true;
    }

    @Override
    public List<UserVO> listFriendRequest(Integer userId) {
        return userDao.getMapper().findRequestUser(userId);
    }

    @Override
    public boolean deleteFriend(Integer friendId) {
        Integer currentId = RequestContent.getCurrentUser().getId();
        Friend friend = friendDao.getRepository().findByUserIdAndFriendId(currentId, friendId);
        if (friend == null) {
            throw new FriendException("该用户不是你的好友哦~");
        }
        friendDao.getRepository().delete(friend);
        friend = friendDao.getRepository().findByUserIdAndFriendId(friendId, currentId);
        friendDao.getRepository().delete(friend);
        return true;
    }

}
