package edu.sandau.chat.controller;

import edu.sandau.chat.enums.RequestFriendsStatusEnum;
import edu.sandau.chat.service.FriendService;
import edu.sandau.chat.service.UserService;
import edu.sandau.chat.vo.AcceptFriendVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private UserService userService;

    /***
     * 发送好友请求
     * @param username
     * @return
     */
    @PostMapping("/request")
    public ResponseEntity<String> addFriendRequest(@RequestBody String username) {
        RequestFriendsStatusEnum statusEnum = userService.requestFriend(username);

        if (statusEnum == RequestFriendsStatusEnum.SUCCESS) {
            return new ResponseEntity<>(HttpStatus.OK);
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
    public String acceptFriend(@RequestBody AcceptFriendVO acceptFriendVO) {
        friendService.addFriendToList(acceptFriendVO.getFriendId());
        return "123";
    }


}
