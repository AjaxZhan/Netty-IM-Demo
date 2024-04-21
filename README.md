# Netty-IM-Demo

---
**中文** | [English](README.en-US.md)


## 简介

`Netty-IM-Demo` 是一个即时通讯的Demo程序，可以作为初学者自学Netty的小实验。

## 功能

- 用户登录和注销
- 用户之间的对话
- 群聊功能（创建/加入/退出群组、列出群聊人数、群聊）
- 空闲检测、定时发送心跳包

## 你能学到什么

- 如何启动Netty服务端/客户端？
  - Netty的NIO模型，Reactor线程模型。
  - Netty中的Channel概念
- 数据载体 ByteBuf
  - ByteBuf的指针
  - ByteBuf的常用API
  - ByteBuf的内存分配
- 长连自定义协议如何设计：魔数/协议版本号/序列化算法/...
- 粘包拆包原理与实践
  - Netty的拆包器
- 如何实现自定义编解码
- pipeline 与 channelHandler
  - pipeline的底层数据结构
  - 责任链设计模式
  - ChannelHandler的生命周期
  - Netty中内置的ChannelHandler
- 定时发心跳怎么做
- 如何进行连接空闲检测
- 优化策略
  - 单例模式改造无状态Handler
  - 压缩Handler
  - 压缩数据包传播路径
  - 统计时长怎么做

## 项目结构

```
├─attribute                      // Netty连接的属性
├─client                         // 客户端
│       └── console                 // 控制台线程类
│       └── handler                 // 响应处理器
├─codec                          // 编解码处理器、拆包处理器
├─handler                        // 通用处理器，如空闲检测
├─protocol                       // 协议
│       └── command                 // 命令常量
│       └── request                 // 请求数据包
│       └── response                // 响应数据包
├─serializer                     // 序列化器
├─server                         // 服务端
├─session                        // 会话
├─utils                          // 工具类
```

## 后续计划

1. 添加客户端。
   1. 基于Electron给IM系统加一个UI界面。
   2. 客户端通过sqlite数据库进行持久化。
   3. 客户端和服务端之间通过Netty进行长连接通信。
2. 支持文件传输：支持传送图片、支持传送视频。
3. 服务端丰富：加入Spring Boot、Redis、MySQL的技术栈，支持聊天记录持久化，支持好友功能，支持群聊持久化功能。
