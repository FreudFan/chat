package edu.sandau.chat.service.impl;

import edu.sandau.chat.dao.*;
import edu.sandau.chat.entity.group.Group;
import edu.sandau.chat.entity.group.GroupRequest;
import edu.sandau.chat.entity.user.Friend;
import edu.sandau.chat.entity.user.User;
import edu.sandau.chat.netty.MsgActionEnum;
import edu.sandau.chat.enums.RequestGroupStatusEnum;
import edu.sandau.chat.entity.group.GroupUser;
import edu.sandau.chat.enums.GroupRoleEnum;
import edu.sandau.chat.exception.GroupException;
import edu.sandau.chat.interceptor.RequestContent;
import edu.sandau.chat.netty.ChannelUtils;
import edu.sandau.chat.netty.DataContent;
import edu.sandau.chat.service.GroupService;
import edu.sandau.chat.vo.GroupRequestVO;
import edu.sandau.chat.vo.GroupVO;
import edu.sandau.chat.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDao groupDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private GroupUserDao groupUserDao;
    @Autowired
    private GroupRequestDao groupRequestDao;
    @Autowired
    private ChannelUtils channelUtils;
    @Autowired
    private FriendDao friendDao;

    @Override
    public Group createGroup(Group group) {
        User currentUser = RequestContent.getCurrentUser();
        group.setMasterId(currentUser.getId());
        group.setFlag(0);
        group = groupDao.getRepository().save(group);

        GroupUser groupUser = new GroupUser();
        groupUser.setGroupId(group.getId());
        groupUser.setUserId(currentUser.getId());
        groupUser.setNickname(currentUser.getName());
        groupUser.setRoleId(GroupRoleEnum.MASTER.value);
        groupUserDao.getRepository().save(groupUser);

        return group;
    }

    @Override
    public void disbandGroup(Integer groupId) {
        Optional<Group> optionalGroup = groupDao.getRepository().findById(groupId);
        if (!optionalGroup.isPresent()) {
            throw new GroupException("群不存在");
        }
        Group group = optionalGroup.get();
        int currentId = RequestContent.getCurrentUser().getId();
        if (currentId != group.getMasterId()) {
            throw new GroupException("用户无权限，只有群主可以解散群哦~");
        }
        group.setFlag(1);
        groupDao.getRepository().save(group);
        // 禁用群
        List<UserVO> userVOS = groupDao.getMapper().queryGroupUsers(groupId);
        List<Integer> userId = userVOS.stream().map(UserVO::getId).collect(Collectors.toList());
        // TODO:向群用户发送群解散通知
        DataContent dataContent = new DataContent();
        dataContent.setAction(MsgActionEnum.DISBAND_GROUP.type);
        dataContent.setExtend(String.valueOf(groupId));
        channelUtils.sendMessageToGroupUser(0, groupId, dataContent);
    }

    @Override
    public List<UserVO> queryGroupUsers(Integer groupId) {
        return groupDao.getMapper().queryGroupUsers(groupId);
    }

    @Override
    public List<GroupVO> searchGroup(String name) {
        int currentId = RequestContent.getCurrentUser().getId();
        //获取模糊查询的所有群实体
        List<GroupVO> selectGroups = groupDao.getMapper().searchGroup(name);
        //获取操作用户的所有群
        List<GroupUser> groups = groupUserDao.getRepository().findAllByUserId(currentId);
        //将模糊搜索到的群中不属于操作用户的，添加进入
        List<GroupVO> finalSearchGroup = new ArrayList<>();
        boolean flag = true;
        for (GroupVO selectGroup : selectGroups) {
            for (GroupUser group : groups) {
                if (selectGroup.getGroupId().equals(group.getGroupId())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                selectGroup.setJoined(false);
            } else {
                selectGroup.setJoined(true);
                flag = true;
            }
            finalSearchGroup.add(selectGroup);
        }
        return finalSearchGroup;

    }

    @Override
    public List<Integer> getGroupUsersId(Integer groupId) {
        return groupDao.getMapper().getGroupUsersId(groupId);
    }

    @Override
    public boolean setGroupUserManageRole(Integer groupId, Integer setUserId) {
        int currentId = RequestContent.getCurrentUser().getId();
        int roleId = groupUserDao.getRepository().findByGroupIdAndUserId(groupId, currentId).getRoleId();
        if (roleId == 2) {
            groupUserDao.getRepository().findByGroupIdAndUserId(groupId, setUserId).setRoleId(1);
            return true;
        } else
            return false;
    }

    @Override
    public boolean removeGroupUserManageRole(Integer groupId, Integer setUserId) {
        int currentId = RequestContent.getCurrentUser().getId();
        int roleId = groupUserDao.getRepository().findByGroupIdAndUserId(groupId, currentId).getRoleId();
        if (roleId == 2) {
            groupUserDao.getRepository().findByGroupIdAndUserId(groupId, setUserId).setRoleId(0);
            return true;
        } else
            return false;

    }

    @Override
    public boolean removeGroupUser(Integer groupId, Integer setUserId) {
        int currentId = RequestContent.getCurrentUser().getId();
        int roleId = groupUserDao.getRepository().findByGroupIdAndUserId(groupId, currentId).getRoleId();
        int setUserRole = groupUserDao.getRepository().findByGroupIdAndUserId(groupId, setUserId).getRoleId();
        if (roleId > setUserRole) {
            groupUserDao.getRepository().deleteByGroupIdAndUserId(groupId, setUserId);
            return true;
        } else
            return false;
    }

    @Override
    public UserVO showGroupUserInfo(Integer groupId, Integer userId) {
        int currentId = RequestContent.getCurrentUser().getId();
        User user = userDao.getRepository().findById(userId).get();
        UserVO userVO = new UserVO();
        Friend friend = friendDao.getRepository().findByUserIdAndFriendId(currentId, userId);
        userVO.setName(user.getName());
        userVO.setGender(user.getGender());
        userVO.setHeadPortrait(user.getHeadPortrait());
        userVO.setIsFriend(false);
        userVO.setGroupId(groupId);
        userVO.setId(userId);
        if (friend != null) {
            userVO.setIsFriend(true);
            userVO.setNickname(friend.getNickname());
        }
        return userVO;
    }

    @Override
    public Group getGroupInfo(Integer groupId) {
        return groupDao.getRepository().findById(groupId).get();
    }

    @Override
    public int getGroupUserRole(Integer groupId) {
        int currentId = RequestContent.getCurrentUser().getId();
        return groupUserDao.getRepository().findByGroupIdAndUserId(groupId, currentId).getRoleId();
    }

    @Override
    public Group changeGroupInfo(Group group) {
        Group getGroup = groupDao.getRepository().findById(group.getId()).get();
        group.setCreateTime(getGroup.getCreateTime());
        group.setMasterId(getGroup.getMasterId());
        group.setFlag(0);
        group = groupDao.getRepository().save(group);
        return group;
    }

    @Override
    public RequestGroupStatusEnum requestGroup(Integer groupId) {
        User currentUser = RequestContent.getCurrentUser();
        Optional<Group> userOptional = groupDao.getRepository().findById(groupId);
        //判断该群号是否存在
        if (!userOptional.isPresent()) {
            return RequestGroupStatusEnum.GROUP_NOT_EXIST;
        }
        //判断用户是否已经加入该群
        List<Group> groups = groupDao.getMapper().queryUserJoinGroup(currentUser.getId());
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getId() == groupId) {
                return RequestGroupStatusEnum.ALREADY_GROUP;
            }
        }

        //五分钟之内不能重复申请
        Optional<Group> groupOptional = groupDao.getRepository().findById(groupId);
        Group acceptGroup = groupOptional.get();
        GroupRequest groupRequest = groupRequestDao.getRepository().findBySendUserIdAndGroupId(currentUser.getId(), groupId);

        //之前没有发送过申请
        if (groupRequest == null) {
            this.saveGroupRequest(acceptGroup.getId());
        } else {
            LocalDateTime requestTime = groupRequest.getRequestDateTime();
            Duration duration = Duration.between(requestTime, LocalDateTime.now());
            long minus = duration.toMinutes();
            // 五分钟内不允许重复申请
            if (minus <= 5) {
                return RequestGroupStatusEnum.TOO_FAST;
            } else {
                groupRequestDao.getRepository().delete(groupRequest);
                this.saveGroupRequest(acceptGroup.getId());
            }
        }
        //发送成功
        return RequestGroupStatusEnum.SUCCESS;
    }

    //同意用户入群申请
    @Override
    public boolean agreeGroupRequest(Integer groupId, Integer sendUserId) {
        // Group group = groupDao.getRepository().findById(groupId).get();
        GroupUser groupUser = new GroupUser();
        groupUser.setGroupId(groupId);
        groupUser.setUserId(sendUserId);
        //默认昵称是他的名字
        groupUser.setNickname(userDao.getRepository().findById(sendUserId).get().getName());
        //默认初始权限为群员
        groupUser.setRoleId(0);
        //添加至群列表
        groupUserDao.getRepository().save(groupUser);
        //从群申请表删除记录
        groupRequestDao.getRepository().deleteByGroupIdAndAndSendUserId(groupId, sendUserId);
        //TODO: 通知全体群员申请人的群申请通过
        //.........
        return true;
    }

    @Override
    public void ignoreGroupRequest(Integer acceptGroupId, Integer sendUserId) {
        groupUserDao.getRepository().deleteByGroupIdAndUserId(acceptGroupId, sendUserId);
    }

    @Cacheable(cacheNames = "groupNickName", key = "#groupId+':'+#userId", unless = "#result.empty")
    @Override
    public String getUserGroupNickname(Integer groupId, Integer userId) {
        return groupUserDao.getRepository().findByGroupIdAndUserId(groupId, userId).getNickname();
    }

    @CacheEvict(cacheNames = "groupNickName", key = "#groupId+':'+#userId")
    @Override
    public boolean changeUserGroupNickname(Integer groupId, Integer userId, String nickName) {
        GroupUser groupUser = groupUserDao.getRepository().findByGroupIdAndUserId(groupId, userId);
        groupUser.setNickname(nickName);
        groupUserDao.getRepository().save(groupUser);
        return true;
    }

    @Override
    public List<GroupRequestVO> listGroupRequest(Integer groupId) {
        int currentId = RequestContent.getCurrentUser().getId();
        int roleId = groupUserDao.getRepository().findByGroupIdAndUserId(groupId, currentId).getRoleId();
        if (roleId == 0)
            throw new GroupException("您的权限不够");
        return groupRequestDao.getMapper().queryGroupRequest(groupId);
    }

    /***
     * 保存申请记录，并提醒目标群
     * @param acceptGroupId
     */
    public void saveGroupRequest(Integer acceptGroupId) {
        GroupRequest groupRequest = new GroupRequest();
        groupRequest.setSendUserId(RequestContent.getCurrentUser().getId());
        groupRequest.setGroupId(acceptGroupId);
        groupRequestDao.getRepository().save(groupRequest);
        //TODO: 向目标群发送入群申请
    }

}
