# 文件接口API /file

### 上传
```
POST /file/up
```
参数
```
Header: 
{
    "Content-Type": "multipart/form-data"
}

Body:
{
    "file": 文件
}
```
返回
```
3 //文件id
```

### 下载文件
```
GET /file/down?id=1
```
参数
```
id = 1 //文件id
```
返回
```
下载文件
```

### 显示图片
```
GET /file/photo?id=29
```
参数
```
id = 1 //文件id
```
返回
```
图片
```

