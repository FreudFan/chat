# 用户接口API /user

### 查询用户（找人）
通过用户名模糊查询，不会搜到自己

```
GET /user/queryUser?name={name}
```
参数
```
name = {name}
```
返回
```
[
    {
        "id": 2,            //用户id
        "name": "user1",    //用户名称
        "gender": 1,        //用户性别
        "isFriend": true    //是好友关系
    },
    {
        "id": 10,
        "name": "user9",
        "gender": 1,
        "isFriend": false
    }
]
```

### 查询指定用户
```
GET /user/queryUser/{userId}
```
参数
```
userId = 10 //目标用户id
```
返回
```
{
    "id": 10,
    "name": "admin",
    "gender": 1,
    "isFriend": false   //是否是好友 是:true 否:false
}
```

### 查询好友列表
```
GET /user/friend
```
参数
```
无
```
返回
```
[
    {
        "groupId": -1,  //分组id为 -1，代表这些好友未分组
        "users": [
            {
                "id": 5,
                "name": "user4",
                "nickname": "昵称4",
                "gender": 4,
                "groupId": -1
            }
        ]
    },
    {
        "groupId": 1,           //分组id
        "groupName": "分组111",   //分组名
        "users": [              //该组中的用户列表
            {
                "id": 2,
                "name": "user1",
                "nickname": "昵称1",
                "gender": 1,
                "groupId": 1
            },
            {
                "id": 4,
                "name": "user3",
                "nickname": "昵称3",
                "gender": 3,
                "groupId": 1
            }
        ]
    },
    {
        "groupId": 2,
        "groupName": "分组2",
        "users": [
            {
                "id": 3,
                "name": "user2",
                "nickname": "昵称2",
                "gender": 2,
                "groupId": 2
            }
        ]
    },
    {
        "groupId": 3,
        "groupName": "分组3",
        "users": []
    }
]
```

### 查询用户信息
```
GET /user/info
```
参数
```
无
```
返回
```
{
    "id": 3,
    "name": "user2",
    "gender": 2,
    "birthday": "2020-06-01",
    "email": "123@123.com2",
    "telephone": "32345678987"
}
```

### 修改用户信息
```
POST /user/info
```
参数
```
{
    "id": 3,
    "name": "user22",
    "gender": 1,
    "birthday": "2020-06-01",
    "email": "123@123.com2",
    "telephone": "32345678987"
}
```
返回
```
{
    "id": 3,
    "name": "user22",
    "gender": 1,
    "birthday": "2020-06-01",
    "email": "123@123.com2",
    "telephone": "32345678987"
}
```

### 查询群组列表
```
GET /user/group
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
        "name": "班群",
        "masterId": 1,
        "info": "2017级cs班群",
        "flag": 0,
        "createTime": "2020-06-10T16:39:47.923"
    },
    {
        "id": 3,
        "name": "班群1",
        "masterId": 1,
        "info": "2017级cs班群1",
        "flag": 0,
        "createTime": "2020-06-10T16:42:53.727"
    },
    {
        "id": 4,
        "name": "班群2",
        "masterId": 1,
        "info": "2017级cs班群2",
        "flag": 0,
        "createTime": "2020-06-10T16:43:02.13"
    }
]
```