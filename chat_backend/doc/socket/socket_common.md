# socket数据包格式和枚举 :8088/ws

### 基本数据格式
```
{
    "action": 1,    //动作类型
    "chatMsg": {    //消息内容实体
        "senderId": 1,  //发送者：用户id
        "receiverId": 2,    //接受者：用户id 
        "groupId": 1,   //接受者：群组id
        "nickname" : "昵称"   //群昵称
        "name" : "用户名"      //用户名
        "msg": "消息内容",  //消息内容
        "msgId": "1,2,3,4"  //用于消息的签收
    },
    "extend": "扩展字段"    //拓展字段
}
```

### action 枚举
```
    CONNECT(1, "第一次(或重连)初始化连接"),
    CHAT(2, "聊天消息"),
    SIGNED(3, "消息签收"),
    KEEPALIVE(4, "客户端保持心跳"),
    PULL_FRIEND(5, "拉取好友"),
    NEW_FRIEND(6, "好友申请通过"),
    NEW_GROUP_MEMBER(7, "新成员加群"),
    DISBAND_GROUP(8, "解散群"),
    REQUEST_FRIEND(9, "通知有好友发起申请"),
    GROUP_CHAT(10, "群聊天消息"),
    GROUP_NOTICE(11, "群公告");
```