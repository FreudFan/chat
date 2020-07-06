# chat_backend

## 后端模块

### 后端技术栈

1. Spring Boot
2. netty nio
3. WebSocket
4. MyBatis
5. Spring Data JPA
6. Alibaba Druid
7. MySQL
8. Gradle

### 架构概览

基于 SpringBoot 技术栈开发的 restful 服务  
数据持久层使用 Spring Data JPA 和 MyBatis 混合开发，单表 CURD 使用 JPA，连表查询使用 MyBatis  
数据库连接池使用 Druid  
数据库采用 MySQL 8  
包管理采用 Gradle 6.3  
WebSocket 接口层采用 Netty 的 Reactor 线程模型  

### 后端架构图

<img src="https://raw.githubusercontent.com/FreudFan/image/master/chat/backend-architecture.PNG" alt="后端架构图" width="700" />

### Netty Websocket

由于IM系统的特殊性，项目采用 ajax 和 Websocket 混合开发  
对于非即时通讯功能，使用 AJAX 进行数据交互，用 Tomcat 作为 HTTP 服务器入口  
对于即时通讯功能，由于对延迟要求较高且并发较大，故采用 WebSocket 进行数据交互，并基于 Netty 实现了主从线程模型(主从NIO线程)，能够大幅度提高性能

<img src="https://raw.githubusercontent.com/FreudFan/image/master/chat/nettynio.PNG" alt="reactor线程模型" width="700" />

### 后端启动时执行过程

<img src="https://raw.githubusercontent.com/FreudFan/image/master/chat/backend-execute.PNG" alt="backend-execute" width="700" />

### Websocket 请求数据流

<img src="https://raw.githubusercontent.com/FreudFan/image/master/chat/socket-dataflow.PNG" alt="socket-dataflow" width="700" />


### AJAX 请求数据流

<img src="https://raw.githubusercontent.com/FreudFan/image/master/chat/ajax-dataflow.png" alt="ajax-dataflow" width="700" />
