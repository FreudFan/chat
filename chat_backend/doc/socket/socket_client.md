#向服务端发送消息

### 注册channel（在用户登录成功后执行）
```
{
    "action": 1,
    "chatMsg": { 
        "senderId": 1
    }
}
```

### 用户点对点发送消息
```
{
    "action": 2,
    "chatMsg": {
        "senderId": 1,  //发送方id
        "receiverId": 2,    //接受方id
        "msg": "你好 2"   //消息文本
    }
}
```

### 签收消息
```
{
    "action": 3,
    "chatMsg": {
        "senderId": 2,
        "msgId": "1,2,3"    //消息id 可以是单条消息签收
    }
}
```

### 群聊发送消息
```
{
    "action": 10,
    "chatMsg": {
        "senderId": 1,  //发送方id
        "groupId": 3,    //群id
        "name" : "用户名", //用户的用户名
        "msg": "你好 群友们"   //消息文本
    }
}
```
