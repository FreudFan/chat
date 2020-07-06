# 聊天记录接口 用户/群 /message

### 分页查询用户聊天记录
```
GET /message/chat/{userId}/{pageNum}/{size}?startDate={startDate}
```
参数
```
userId : 2  //目标用户id
pageNum: 1  //页数
size : 3    //单页个数 可不填 默认20
startDate : 2020-06-06  //起始时间 可不填 如果有的话是正序查询

*默认查询返回结果为 逆序 排列, 即最新消息在前
*如果传了 startDate 参数, 则查询结果正序排列, 即老消息在前，
```
返回
```
{
    "total": 8,
    "list": [
        {
            "id": 1,
            "senderUserId": 1,
            "acceptUserId": 2,
            "message": "你好 2",
            "signFlag": 1,
            "createTime": "2020-06-01 17:41:22"
        },
        {
            "id": 2,
            "senderUserId": 2,
            "acceptUserId": 1,
            "message": "你好 1",
            "signFlag": 1,
            "createTime": "2020-06-02 17:47:11"
        },
        {
            "id": 3,
            "senderUserId": 1,
            "acceptUserId": 2,
            "message": "你好 22",
            "signFlag": 1,
            "createTime": "2020-06-02 17:50:10"
        }
    ],
    "pageNum": 1,
    "pageSize": 3,
    "size": 3,
    "startRow": 1,
    "endRow": 3,
    "pages": 3,
    "prePage": 0,
    "nextPage": 2,
    "isFirstPage": true,
    "isLastPage": false,
    "hasPreviousPage": false,
    "hasNextPage": true,
    "navigatePages": 8,
    "navigatepageNums": [
        1,
        2,
        3
    ],
    "navigateFirstPage": 1,
    "navigateLastPage": 3
}
```

### 分页查询群组聊天记录
```
GET /message/group/{groupId}/{pageNum}/{size}?startDate={startDate}
```
参数
```
groupId : 3  //目标群id
pageNum: 1  //页数
size : 2    //单页个数 可不填 默认20
startDate : 2020-06-06  //起始时间 可不填 如果有的话是正序查询

*默认查询返回结果为 逆序 排列, 即最新消息在前
*如果传了 startDate 参数, 则查询结果正序排列, 即老消息在前，
```
返回
```
{
    "total": 4,
    "list": [
        {
            "id": 4,
            "senderUserId": 1,
            "groupId": 3,
            "message": "你好 群友们",
            "createTime": "2020-06-06 17:25:36"
        },
        {
            "id": 5,
            "senderUserId": 1,
            "groupId": 3,
            "message": "你好 群友们",
            "createTime": "2020-06-07 17:26:32"
        }
    ],
    "pageNum": 1,
    "pageSize": 2,
    "size": 2,
    "startRow": 1,
    "endRow": 2,
    "pages": 2,
    "prePage": 0,
    "nextPage": 2,
    "isFirstPage": true,
    "isLastPage": false,
    "hasPreviousPage": false,
    "hasNextPage": true,
    "navigatePages": 8,
    "navigatepageNums": [
        1,
        2
    ],
    "navigateFirstPage": 1,
    "navigateLastPage": 2
}
```