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