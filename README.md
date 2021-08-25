## 开源论坛社区

## 资料
[Spring 文档](https://spring.io/guides/gs/serving-web-content/)


## 工具
[Boostrap文档](https://v3.bootcss.com/getting-started/#download)

[Github OAuth](https://docs.github.com/cn/developers/apps/building-oauth-apps/creating-an-oauth-app)

## 脚本
```sql
create table USER
(
    ID           INT auto_increment,
    ACCOUNT_ID   VARCHAR(100),
    NAME         VARCHAR(50),
    TOKEN        CHAR(36),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
    constraint USER_PK
        primary key (ID)
);
```
