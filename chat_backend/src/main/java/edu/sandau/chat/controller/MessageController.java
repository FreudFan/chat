package edu.sandau.chat.controller;

import com.github.pagehelper.PageInfo;
import edu.sandau.chat.entity.group.GroupMessage;
import edu.sandau.chat.entity.user.ChatMessage;
import edu.sandau.chat.interceptor.RequestContent;
import edu.sandau.chat.netty.ChatMsg;
import edu.sandau.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService chatMessageService;
    @Autowired
    private MessageService groupMessageService;

    @GetMapping("/unread")
    public List<ChatMsg> getUnReadMsgList() {
        int currentId = RequestContent.getCurrentUser().getId();
        return chatMessageService.getUnReadMsgList(currentId);
    }

    /***
     * 分页查询用户聊天记录
     * @param userId 目标用户id
     * @param pageNum 页数
     * @param size 单页个数 默认20
     * @param startDate 开始查询时间 如果有的话是正序查询
     * @return
     */
    @GetMapping(value = {
            "/chat/{userId}/{pageNum}",
            "/chat/{userId}/{pageNum}/{size}"})
    public PageInfo<ChatMessage> getChatMessageList(@PathVariable("userId") Integer userId,
                                                    @PathVariable("pageNum") Integer pageNum,
                                                    @PathVariable(name = "size", required = false) Integer size,
                                                    @RequestParam(name = "startDate", required = false) LocalDate startDate) {
        return chatMessageService.getHistoryChatMessage(userId, pageNum, size, startDate);
    }

    /***
     * 分页查询群聊天记录
     * @param groupId 目标群id
     * @param pageNum 页数
     * @param size 单页个数 默认20
     * @param startDate 开始查询时间 如果有的话是正序查询
     * @return
     */
    @GetMapping(value = {
            "/group/{groupId}/{pageNum}",
            "/group/{groupId}/{pageNum}/{size}"})
    public PageInfo<GroupMessage> getGroupMessageList(@PathVariable("groupId") Integer groupId,
                                                      @PathVariable("pageNum") Integer pageNum,
                                                      @PathVariable(name = "size", required = false) Integer size,
                                                      @RequestParam(name = "startDate", required = false) LocalDate startDate) {
        return groupMessageService.getHistoryGroupMessage(groupId, pageNum, size, startDate);
    }

}
