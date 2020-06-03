package edu.sandau.chat.controller;

import edu.sandau.chat.entity.FriendGroup;
import edu.sandau.chat.enums.OperatorFriendRequestTypeEnum;
import edu.sandau.chat.enums.RequestFriendsStatusEnum;
import edu.sandau.chat.exception.FormException;
import edu.sandau.chat.interceptor.RequestContent;
import edu.sandau.chat.service.FriendService;
import edu.sandau.chat.vo.AcceptFriendVO;
import edu.sandau.chat.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private FriendService friendService;

    /***
     * 发送好友请求
     * @param userId
     * @return
     */
    @PostMapping("/request")
    public ResponseEntity<String> addFriendRequest(@RequestParam("userId") Integer userId) {
        RequestFriendsStatusEnum statusEnum = friendService.requestFriend(userId);
        if (statusEnum == RequestFriendsStatusEnum.SUCCESS) {
            return new ResponseEntity<>(statusEnum.name, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(statusEnum.name, HttpStatus.BAD_REQUEST);
        }
    }

    /***
     * 处理好友请求
     * @param acceptFriendVO
     * @return
     */
    @PostMapping("/accept")
    public UserVO processFriendRequest(@RequestBody AcceptFriendVO acceptFriendVO) {
        int operationType = acceptFriendVO.getOperationType();
        if(operationType == OperatorFriendRequestTypeEnum.PASS.value) {
            UserVO friend = friendService.agreeFriendRequest(acceptFriendVO.getSendUserId());
            return friend;
        } else if(operationType == OperatorFriendRequestTypeEnum.IGNORE.value){
            friendService.ignoreFriendRequest(acceptFriendVO.getSendUserId());
            return null;
        } else {
            throw new FormException("操作代码错误");
        }
    }

    @GetMapping("/friendRequest")
    public List<UserVO> listFriendRequest() {
        int currentId = RequestContent.getCurrentUser().getId();
        return friendService.listFriendRequest(currentId);
    }

    /***
     * 修改好友分组名
     * @param friendGroup
     * @return
     */
    @PostMapping("/modifyGroup")
    public FriendGroup modifyGroupName(@RequestBody FriendGroup friendGroup)
    {
        friendService.modifyFriendGroupName(friendGroup);
        return friendGroup;
    }

    /***
     * 删除好友分组
     * @param friendGroup
     * @return
     */
    @PostMapping("/deleteGroup")
    public boolean deleteGroup(@RequestBody FriendGroup friendGroup)
    {
        return friendService.deleteFriendGroup(friendGroup);
    }

}
