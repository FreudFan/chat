# 权限接口API /auth

### 登录

```
POST /login
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

### 注册

```
POST /register
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
