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
包管理采用 Gradle-6.3  
WebSocket 接口层采用 Netty 的 Reactor 线程模型  



