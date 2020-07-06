# 用户群接口API /group

### 创建群
```
POST /group/create
```
参数
```
{
"name" : "班群",   //群名
"info" : "2017级cs班群"    //群介绍
}
```
返回
```
{
    "id": 2,
    "name": "班群",
    "masterId": 1,
    "info": "2017级cs班群",
    "createTime": "2020-06-10T16:43:02.13"
}
```

### 解散群 
```
POST /group/dissolute/{groupId}
```
参数
```
groupId : 1
```
返回
```
解散群 假删除
只有群主能够解散群
@param groupId
@throws "群不存在"
@throws "用户无权限，只有群主可以解散群哦~"
```

### 查询群成员列表
```
GET /group/members/{groupId}
```
参数
```
groupId : 1
```
返回
```
[
    {
        "id": 1,
        "name": "昵称",
        "nickname": "昵称",
        "gender": 1,
        "groupId": 2    //群角色 0:群员 1:管理 2:群主
    },
    {
        "id": 2,
        "name": "昵称1",
        "nickname": "昵称1",
        "gender": 1,
        "groupId": 0
    }
]
```

### 发送申请加群请求
```
POST /group/requestGroup/{groupId}
```
参数
```
groupId : 3
```
返回
```
  ok

 规则：
 0. 申请成功，返回[OK]
 1. 搜索的群如果不存在，返回[未找到群...]
 2. 搜索已添加进入的群，返回[你已在群中...]
 3. 在五分钟内重复申请，返回[已经申请过啦...]

```

### 同意/忽略申请加群请求
```
POST /group/acceptGroup
```
参数
```
sendGroupId:3,
sendUserId:2,
operationType:1

```
返回
```
  true

 规则,operationType数值：
 0. false,忽略申请
 1. true,同意申请
 其他数值：操作代码错误

sendGroupId输入的是没有创建的群号的话，返回没有该群号。

```

### 查询群名称
通过群名称模糊查询，不会查到操作用户已加入的群

```
GET /group/queryGroup/{name}
```
参数
```
name = {name}
```
返回
```
[
     {
            "groupId": 2,
            "name": "班群",
            "joined": false //该用户是否已加入该群
        },
        {
            "groupId": 3,
            "name": "班群1",
            "joined": false
        },
        {
            "groupId": 4,
            "name": "班群2",
            "joined": false
        }
]
```

### 查询用户的群权限
```
GET /group/queryUserRole/{groupId}
```
参数
```
groupId = 2
```
返回
```
{
  2
}
```

### 查询群资料
```
GET /group/infoGroup/{groupId}
```
参数
```
groupId = 3
```
返回
```
{
    "id": 3,
    "name": "班群1",
    "masterId": 1,
    "info": "2017级cs班群1",
    "flag": 0,
    "createTime": "2020-06-10T16:42:53.727"
}
```

### 编辑群资料
```
POST /group/changeGroupInfo
```
参数
```
{

   "id":"2",
   "info":"临时更改aaa2017级cs班群",
   "name":"更新班群",
   "master_id":"1"
   
}
```
返回
```
{
    "id": 2,
    "name": "更新班群",
    "masterId": 1,
    "info": "临时更改aaa2017级cs班群",
    "createTime": "2020-06-10T16:39:47.923"
}
```

### 上传群文件
```
POST /group/uploadGroupAttachment/{groupId}
```
参数
```
{
    （form-data）
     file:上传个文件
     groupId:2
}
```
返回
```
{
    5  (群内第几个文件)
}
```

### 显示群文件列表信息
```
get /group/showGroupAttachmentList/{groupId}
```
参数
```
{
  groupId:2
}
```
返回
```
 {
        "fileId": "4",
        "fileName": "测试",
        "contentSize": "20",
        "userVO": {
            "id": 1,
            "nickname": "昵称"
        }
    },
    {
        "fileId": "8",
        "fileName": "测试2",
        "contentSize": "24",
        "userVO": {
            "id": 1,
            "nickname": "昵称"
        }
    }
```

### 删除群文件
```
POST /group/deleteGroupAttachment/{groupId}/{fileId}
```
参数
```
  groupId:2
  fileId:8
```
返回
```
   true
```

### 群主设置用户为管理员权限
```
POST /group/setUserManageRole/{groupId}/{userId}
```
参数
```
  groupId:3,
  userId:2
```
返回
```
   true
```

### 群主移除用户为管理员的权限
```
POST /group/removeUserManageRole/{groupId}/{userId}
```
参数
```
  groupId:3,
  userId:2
```
返回
```
   true
```

### 移除群用户功能（群主能移除所有用户，管理员只能移除普通用户）
```
POST /group//removeGroupUser/{groupId}/{userId}
```
参数
```
  groupId:3,
  userId:2
```
返回
```
   true
```

### 显示群用户信息（如果查询的该用户是自己的好友的话，额外显示该用户的备注）
```
Get /group/showGroupUserInfo/{groupId}/{userId}
```
参数
```
  groupId:3,
  userId:1（被查询的用户id）
```
返回
```
  {
      "id": 1,
      "name": "liukairui",
      "nickname": "昵称",
      "gender": 1,
      "isFriend": true,
      "groupId": 3
  }
```

### 增加群公告(只有群主和管理员能添加)
```
POST /group/addNotice
```
参数
```
 {
     "groupId":"3",
     "title":"标题1111",
     "content":"测试内容111"
 }
```
返回
```
 true
```

### 删除群公告(只有群主和管理员能删除)
```
POST /group/deleteNotice
```
参数(param)
```
  groupId:3,
  noticeId:2
```
返回
```
 true
```

### 修改群公告(只有群主和管理员能删除)
```
POST /group/updateNotice
```
参数
```
{
    "id":"1",
    "title":"修改公告测试",
    "content":"修改内容213123"
}
```
返回
```
 true
```

### 显示单个群公告
```
POST /group/showNotice/{noticeId}
```
参数
```
  3
```
返回
```
   {
       "id": 3,
       "title": "标题1111",
       "content": "测试内容111",
       "ownerId": 1,
       "groupId": 3,
       "createTime": "2020-06-22T14:55:13.889493",
       "updateTime": "2020-06-22T14:55:13.889493"
   }
```

### 显示群公告列表
```
POST /group/showNoticeList/{groupId}
```
参数
```
  3
```
返回
```
    {
          "id": 1,
          "title": "修改公告测试",
          "content": "修改内容213123",
          "ownerId": 1,
          "groupId": 3,
          "createTime": "2020-06-22 14:44:52",
          "updateTime": "2020-06-22 15:55:59"
    },
    {
          "id": 3,
          "title": "标题1111",
          "content": "测试内容111",
          "ownerId": 1,
          "groupId": 3,
          "createTime": "2020-06-22 14:55:13",
          "updateTime": "2020-06-22 14:55:13"
    }
```



### 显示群请求列表
```
get /group/searchGroupRequest/{groupId}
```
参数
```
{
  groupId:2
}
```
返回
```
 [
      {
             "sendUserId": 3,
             "groupId": 2,
             "userVo": {
                 "id": 3,
                 "name": "Test1",
                 "gender": 1
             },
             "requestDateTime": "2020-06-26 15:51:37"
         },
         {
             "sendUserId": 10,
             "groupId": 2,
             "userVo": {
                 "id": 10,
                 "name": "admin",
                 "gender": 1
             },
             "requestDateTime": "2020-06-30 18:50:28"
         }
 ]
```