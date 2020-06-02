package edu.sandau.chat.controller;

import edu.sandau.chat.entity.User;
import edu.sandau.chat.service.FriendService;
import edu.sandau.chat.service.UserService;
import edu.sandau.chat.vo.AcceptFriendVO;
import edu.sandau.chat.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private UserService userService;

    @PostMapping("/request")
    public ResponseEntity<String> addFriendRequest(@RequestBody String username) {
        User user = userService.preconditionRequestFriend(username);

        return new ResponseEntity<>(HttpStatus.OK);
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
