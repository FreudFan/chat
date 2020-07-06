# 权限接口API /auth

### 登录
```
POST /auth/login
```
参数
```
{
	"text": "123@123.com", //邮箱或手机号
	"password": "123"
}
```
返回
```
{
    "id": 3,
    "name": "11111",
    "gender": 1,
    "email": "123@123.com",
    "telephone": "12345678912"
}
```

### 注销
```
POST /auth/logout
```
参数
```
无
```
返回
```
无
```

### 注册
```
POST /auth/register
```
参数
```
{
	"name": "昵称",
	"password": "password",
	"gender": 1,
	"email": "123@321.com",
	"telephone": 12345678987
}
```
返回
```
{
    "id": 2,
    "name": "昵称",
    "gender": 1,
    "email": "123@321.com",
    "telephone": "12345678987"
}
```

### 用户信息查重
```
POST /auth/infoCheck
```
参数
```
{
    "type" : 0,     //字段类型 枚举
    "value": "123@321.com"
}

type: 0:邮箱 1:手机号 2:用户名
```
返回
```
无重复字段: true
有重复字段,查重失败: false
```
