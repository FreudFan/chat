# 响应后端消息

### 好友通知
```
{
    "action": 9,
    "chatMsg": {
        "senderId": 11, //申请者id
        "receiverId": 1,
        "groupId": null,
        "msg": null,
        "msgId": null
    },
    "extend": {     //申请者信息
        "id": 1,
        "name": "liukairui",
        "nickname": null,
        "gender": 1,
        "headPortrait": null,
        "isFriend": null,
        "groupId": null
    }
}
```

### 接受点对点消息
```
{
    "action": 2,
    "chatMsg": {
        "senderId": 1,  //发送用户id
        "receiverId": 2,
        "groupId": null,
        "msg": "你好 2",  //消息文本
        "msgId": null
    },
    "extend": null
}
```

### 申请的好友请求已通过,需要重新拉取好友列表
```
{
    "action": 5,
    "chatMsg": {
        "senderId": 3,  //新好友id
        "receiverId": 1,
        "groupId": null,
        "msg": null,
        "msgId": null
    },
    "extend": null
}
```

### 接受群消息
```
{
    "action": 10,
    "chatMsg": {
        "senderId": 1,  //  发送用户id
        "receiverId": null,
        "groupId": 3,   // 群消息
        "nickname": "昵称",    //群昵称
        "name" : "用户名", //用户的用户名
        "msg": "你好 群友们",    //消息文本
        "msgId": null
    },
    "extend": null
}
```

### 群公告推送
```
{
    "action": 11,
    "chatMsg": null,
    "extend": "{
                   "id": 7, //公告id
                   "title": "测试发送1111", //公告标题
                   "content": "测试发送11111",  //公告内容
                   "ownerId": 1,              //发送者id
                   "groupId": 3,              //群id
                   "createTime": "2020-06-23 19:13:23", //公告时间
                   "updateTime": "2020-06-23 19:13:23"
               }"
}
```
