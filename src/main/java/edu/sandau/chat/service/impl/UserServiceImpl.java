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
import edu.sandau.chat.enums.UserFormTypeEnum;
import edu.sandau.chat.exception.RegisterException;
import edu.sandau.chat.interceptor.RequestContent;
import edu.sandau.chat.service.UserService;
import edu.sandau.chat.vo.MyFriendsVO;
import edu.sandau.chat.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private FriendGroupDao friendGroupDao;
    @Autowired
    private FriendDao friendDao;
    @Autowired
    private FriendRequestDao friendRequestDao;

    @Override
    public User login(UserFormTypeEnum loginValue, String loginName, String password) {
        User user = userDao.getMapper().login(loginValue.name, loginName, password);
        return user;
    }

    @Override
    public User register(User user) {
        String telephone = user.getTelephone();
        String email = user.getEmail();
        if(this.check(UserFormTypeEnum.TELEPHONE, telephone)
                && this.check(UserFormTypeEnum.EMAIL, email)) {
            return userDao.getRepository().save(user);
        } else {
            throw new RegisterException("用户名或手机号已存在");
        }
    }

    @Override
    public boolean check(UserFormTypeEnum userFormTypeEnum, String value) {
        return userDao.getMapper().count(userFormTypeEnum.name, value)  == 0;
    }

    @Override
    public List<UserVO> searchUser(String name) {
        int currentId = RequestContent.getCurrentUser().getId();
        List<UserVO> users = userDao.getMapper().searchUser(UserFormTypeEnum.NAME.name, name, currentId);
        List<Friend> friends = friendDao.getRepository().findAllByUserId(currentId);
        for(UserVO userVO: users) {
            userVO.setIsFriend(false);
            for(Friend friend: friends) {
                if(userVO.getId().equals(friend.getFriendId())) {
                    userVO.setIsFriend(true);
                    break;
                }
            }
        }
        return users;
    }

    @Override
    public RequestFriendsStatusEnum requestFriend(String username) {
        User currentUser = RequestContent.getCurrentUser();
        User acceptUser = userDao.getRepository().findAllByName(username);
        if(acceptUser == null) {
            return RequestFriendsStatusEnum.USER_NOT_EXIST;
        }
        if(acceptUser.getName().equals(currentUser.getName())) {
            return RequestFriendsStatusEnum.NOT_YOURSELF;
        }
        Friend friend = friendDao.getRepository().findAllByUserIdAndFriendId(currentUser.getId(), acceptUser.getId());
        if(friend != null) {
            return RequestFriendsStatusEnum.ALREADY_FRIENDS;
        }

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSendUserId(RequestContent.getCurrentUser().getId());
        friendRequest.setAcceptUserId(acceptUser.getId());
        friendRequestDao.getRepository().save(friendRequest);

        //TODO: 向目标用户发送好友申请
        return RequestFriendsStatusEnum.SUCCESS;
    }

    @Override
    public List<MyFriendsVO> queryMyFriends(Integer userId) {
        List<UserVO> users = userDao.getMapper().queryFriendsList(userId);
        List<FriendGroup> groups = friendGroupDao.getRepository().findAllByUserId(userId);

        //TODO 转换对象为vo
        List<MyFriendsVO> myFriendsVOList = new ArrayList<>();
        MyFriendsVO myFriendsVO = new MyFriendsVO();
        myFriendsVO.setUsers(users);
        Iterator it = groups.iterator();
        Iterator itusers = users.iterator();
        int index=0;
        for(int i=0;i<groups.size();i++) {
            List<UserVO> userVOList = new ArrayList<>();
            for(int j=0;j<users.size();j++) {
                if (users.get(j).getGroupId().equals(groups.get(i).getId()))
                {
                    userVOList.add(users.get(j));
                }
            }
            MyFriendsVO tempMyFriendsVO = new MyFriendsVO();
            tempMyFriendsVO.setUsers(userVOList);
            tempMyFriendsVO.setGroupId(groups.get(i).getId());
            myFriendsVOList.add(tempMyFriendsVO);
        }
        return myFriendsVOList;
    }
}
