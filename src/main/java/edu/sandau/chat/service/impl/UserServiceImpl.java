package edu.sandau.chat.service.impl;

import edu.sandau.chat.dao.FriendDao;
import edu.sandau.chat.dao.FriendGroupDao;
import edu.sandau.chat.dao.UserDao;
import edu.sandau.chat.entity.Friend;
import edu.sandau.chat.entity.FriendGroup;
import edu.sandau.chat.entity.User;
import edu.sandau.chat.enums.UserFormTypeEnum;
import edu.sandau.chat.exception.FriendException;
import edu.sandau.chat.exception.RegisterException;
import edu.sandau.chat.interceptor.RequestContent;
import edu.sandau.chat.service.UserService;
import edu.sandau.chat.vo.MyFriendsVO;
import edu.sandau.chat.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public User login(UserFormTypeEnum loginValue, String loginName, String password) {
        User user = userDao.getMapper().login(loginValue.getName(), loginName, password);
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
        return userDao.getMapper().count(userFormTypeEnum.getName(), value)  == 0;
    }

    @Override
    public List<UserVO> searchUser(String name) {
        int currentId = RequestContent.getCurrentUser().getId();
        List<UserVO> users = userDao.getMapper().searchUser(UserFormTypeEnum.NAME.getName(), name, currentId);
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
    public User preconditionRequestFriend(String username) {
        int currentId = RequestContent.getCurrentUser().getId();
        User user = userDao.getRepository().findAllByNameAndIdIsNotIn(username, currentId);
        if(user == null) {
            throw new FriendException("没有找到用户" + username);
        }
        Friend friend = friendDao.getRepository().findAllByUserIdAndFriendId(user.getId(), currentId);
        if(friend != null) {
            throw new FriendException("该用户已经是你的好友啦");
        }
        return user;
    }

    @Override
    public List<MyFriendsVO> queryMyFriends(Integer userId) {
        List<UserVO> users = userDao.getMapper().queryFriendsList(userId);
        List<FriendGroup> groups = friendGroupDao.getRepository().findAllByUserId(userId);

        //TODO 转换对象为vo
//        List<MyFriendsVO> myFriendsVOList = new ArrayList<>();
//        groups.forEach(group ->{
//            MyFriendsVO friendsVO = new MyFriendsVO();
//            friendsVO.setGroupId(group.getId());
//            friendsVO.setGroupName(group.getName());
//            friendsVO.setUsers(users.stream().filter());
//        });

        return null;
    }
}
