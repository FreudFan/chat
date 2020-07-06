package edu.sandau.chat.service.impl;

import edu.sandau.chat.dao.*;
import edu.sandau.chat.entity.group.Group;
import edu.sandau.chat.entity.user.Friend;
import edu.sandau.chat.entity.user.FriendGroup;
import edu.sandau.chat.entity.user.User;
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
    private GroupDao groupDao;
    @Autowired
    private GroupUserDao groupUserDao;

    @Override
    public User login(UserFormTypeEnum loginValue, String loginName, String password) {
        User user = userDao.getMapper().login(loginValue.name, loginName, password);
        return user;
    }

    @Override
    public User register(User user) {
        String telephone = user.getTelephone();
        String email = user.getEmail();
        if (this.check(UserFormTypeEnum.TELEPHONE, telephone)
                && this.check(UserFormTypeEnum.EMAIL, email)) {
            return userDao.getRepository().save(user);
        } else {
            throw new RegisterException("用户名或手机号已存在");
        }
    }

    @Override
    public boolean check(UserFormTypeEnum userFormTypeEnum, String value) {
        return userDao.getMapper().count(userFormTypeEnum.name, value) == 0;
    }

    @Override
    public List<UserVO> searchUser(String name) {
        int currentId = RequestContent.getCurrentUser().getId();
        List<UserVO> users = userDao.getMapper().searchUser(UserFormTypeEnum.NAME.name, name, currentId);
        List<Friend> friends = friendDao.getRepository().findAllByUserId(currentId);
        for (UserVO userVO : users) {
            userVO.setIsFriend(false);
            for (Friend friend : friends) {
                if (userVO.getId().equals(friend.getFriendId())) {
                    userVO.setIsFriend(true);
                    break;
                }
            }
        }
        return users;
    }

    @Override
    public List<MyFriendsVO> queryMyFriends(Integer userId) {
        List<UserVO> users = userDao.getMapper().queryFriendsList(userId);
        List<FriendGroup> groups = friendGroupDao.getRepository().findAllByUserId(userId);

        List<MyFriendsVO> myFriendsVOList = new ArrayList<>();
        //处理未分组情况，未分组的好友 groupId 为 -1
        MyFriendsVO friendsVO = new MyFriendsVO();
        friendsVO.setGroupId(-1);
        friendsVO.setGroupName("我的好友"); //未分组用户的 默认分组
        List<UserVO> userVOS = new ArrayList<>();
        for (UserVO user : users) {
            if (user.getGroupId() == -1) {
                userVOS.add(user);
            }
        }
        friendsVO.setUsers(userVOS);
        myFriendsVOList.add(friendsVO);

        for (FriendGroup group : groups) {
            List<UserVO> userVOList = new ArrayList<>();
            for (UserVO user : users) {
                if (user.getGroupId().equals(group.getId())) {
                    userVOList.add(user);
                }
            }
            MyFriendsVO tempMyFriendsVO = new MyFriendsVO();
            tempMyFriendsVO.setUsers(userVOList);
            tempMyFriendsVO.setGroupId(group.getId());
            tempMyFriendsVO.setGroupName(group.getName());

            myFriendsVOList.add(tempMyFriendsVO);
        }
        return myFriendsVOList;
    }

    @Override
    public User getUserInfo(Integer userId) {
        return userDao.getRepository().findById(userId).get();
    }

    @Override
    public User changeUserInfo(User target) {
        User origin = userDao.getRepository().findById(target.getId()).get();
        String telephone = origin.getTelephone();
        String _telephone = target.getTelephone();
        if (!_telephone.equals(telephone)) {
            if (!this.check(UserFormTypeEnum.TELEPHONE, _telephone)) {
                throw new RegisterException("手机号已存在");
            }
        }
        String email = origin.getEmail();
        String _email = target.getEmail();
        if (!_email.equals(email)) {
            if (!this.check(UserFormTypeEnum.EMAIL, _email)) {
                throw new RegisterException("用户名已存在");
            }
        }
        target.setPassword(origin.getPassword());
        target.setCreateTime(origin.getCreateTime());
        target = userDao.getRepository().save(target);
        return target;
    }

    @Override
    public List<Group> queryMyGroups(Integer userId) {
        return groupDao.getMapper().queryUserJoinGroup(userId);
    }

    @Override
    public UserVO getUserVOById(Integer userId) {
        UserVO userVO = userDao.getMapper().findUserVOById(userId);
        Integer currentId = RequestContent.getCurrentUser().getId();
        if (friendDao.getRepository().findByUserIdAndFriendId(currentId, userId) != null) {
            userVO.setIsFriend(true);
        } else {
            userVO.setIsFriend(false);
        }
        return userVO;
    }
}
