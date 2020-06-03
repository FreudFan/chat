# 好友接口API /friend

### 发送好友申请

```
POST /friend/request
Content-Type: multipart/form-data
```
参数
```
userId = 4  //目标用户id
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
    "gender": 1
}
```
