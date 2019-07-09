# FREEWAY开发框架 #


## svn地址 ##
http://svn.gs.chinahrd.net/svn/freeway/branches/base-v1.0.0/freeway-parent

## 开发环境搭建 ##
- IDE  建议IDEA，安装插件Lombok、MyBatisX
- Zookeeper 建议zookeeper-3.4.13，安装时将conf目录下的zoo_sample.cfg文件复制一份，改名zoo.cfg
- Redis 如果是windows版本，建议安装Redis-x64-3.2.100
- 数据库 开发建议采用mysql或者pg（但框架要支持多种数据库)
- Maven 建议设置阿里云仓库地址

```
<mirrors>   
   <mirror>
      <id>alimaven</id>      
      <mirrorOf>central</mirrorOf>  
      <name>aliyun maven</name>
      <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
    </mirror>
</mirrors>
```

## 框架模块说明 ##
- 逻辑架构图
![逻辑架构图](doc/images/freeway.jpg)

- freeway-parent  项目入口
- freeway-generator 项目代码自动生成模块，辅助模块，不会部署到服务器
   1. PlatformGenerator  平台系统代码自动生成工具类
   2. BusinessGenerator  业务系统代码自动生成工具类
   3. db 里面有框架示例对应的数据库备份sql
- freeway-common  与具体业务无关的一些通用工具类模块
- freeway-facade  facade模块入口
   1. freeway-base-facade  通用基础的facade
   2. freeway-business-facade 业务系统facade，依赖base
   3. freeway-platform-facade 平台系统facade，依赖base
   4. freeway-pubic-facade 待扩展（假如需要），发布给其他系统集成的facade
- freeway-support support模块入口，主要用来做依赖管理和功能支持
   1. freeway-dubbo-support  引入对dubbo的支持
   2. freeway-web-support 引入web的一些通用支持，包括定时任务、日志、参数校验等

- ***freeway-platform-admin*** 平台管理系统，服务和web集成部署，不用dubbo
- ***freeway-business-service*** 业务系统服务端，业务逻辑实现，发布到dubbo注册中心
- ***freeway-business-web***  业务系统web服务端，通过dubbo调用service端服务，同时给各前端提供服务接口


## 通用约定 ##
- 平台系统实体基类为BaseEntity
- 业务系统实体基类为BusinessEntity
- 服务请求参数基类，如果是分页请求为BasePageRequest，否则为BaseRequest

## 示例 ##
- 平台系统 sys模块，做了一个租户分页查询的例子，后续可在此基础上扩展功能
- 业务系统 demo模块，做了一个用户分页查询的例子，纯演示，后续会删除此模块代码