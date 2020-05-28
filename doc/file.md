# 文件接口API /file

### 上传

```
POST /up
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
