# 好友接口API /friend

### 发送好友申请
```
POST /friend/request/{id}
```
参数
```
request/3  //目标用户id
```
返回
```
OK
 规则：
 1. 搜索的用户如果不存在，返回[没有找到用户]
 2. 搜索账号是你自己，返回[不能添加自己]
 3. 搜索的朋友已经是你的好友，返回[该用户已经是你的好友啦]
 4. 在五分钟内重复申请，返回[已经申请过啦...]
 5. 申请成功，返回[OK]
```

### 处理好友申请
```
POST /friend/accept
```
参数
```
{
	"sendUserId": "1",
	"operationType": "0" // 0:忽略 1:通过
}
```
返回
```
[忽略]操作：无返回

[通过]操作：
{
    "id": 1,
    "name": "user",
    "nickname": "user",
    "gender": 1,
    "groupId": -1
}
```

### 新增/修改 好友分组
```
POST /friend/modifyGroup
```
参数
```
{
    "id": "1", //如果是新增，不要传id到后端
	"name": "分组11",
	"userId": "1"
}
```
返回
```
{
    "id": 1,
    "name": "分组1",
    "userId": 1
}
```

### 删除 好友分组
删除用户分组,分组内必须没有用户
```
POST /friend/deleteGroup
```
参数
```
{
	"id": "1",  //必须要传此id
	"name": "分组111",
	"userId": "1"
}
```
返回
```
删除成功：true
删除失败：false
```

#好友通知（有哪些人向我发送了好友请求）
```
GET /friend/friendRequest
```
参数
```
无
```
返回
```
[
    {
        "id": 2,
        "name": "user1",
        "gender": 1
    },
    {
        "id": 4,
        "name": "user3",
        "gender": 3
    }
]
```

#查询好友信息
```
GET /friend/info/{id}
```
参数
```
info/2  //id是用户id
```
返回
```
{
    "id": 2,
    "name": "liqi",
    "nickname": "liqi",
    "gender": 1,
    "groupId": -1
}

如果查询非好友信息，会提示："该用户不是你的好友哦~"
```

#删除好友（保留聊天记录）
```
POST /friend/delete/{id}
```
参数
```
delete/3 //id是用户id
```
返回
```
true

如果删除的不是好友，会提示："该用户不是你的好友哦~"
```