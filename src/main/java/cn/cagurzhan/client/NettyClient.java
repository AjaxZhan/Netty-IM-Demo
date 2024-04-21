package cn.cagurzhan.client;

import cn.cagurzhan.client.console.ConsoleCommandManager;
import cn.cagurzhan.client.console.LoginConsoleCommand;
import cn.cagurzhan.client.handler.*;
import cn.cagurzhan.codec.PacketCodeCHandler;
import cn.cagurzhan.codec.Spliter;
import cn.cagurzhan.utils.SessionUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * 客户端
 * @author AjaxZhan
 */
public class NettyClient {
    /**
     * 最大连接重试次数
     */
    private static final int MAX_RETIRES = 5;

    /**
     * 连接超时时间(ms)
     */
    private static final int CONNECT_TIMEOUT = 5000;

    /**
     * 服务器地址
     */
    private static final String HOST = "127.0.0.1";
    /**
     * 服务器端口
     */
    private static final int PORT = 8000;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,CONNECT_TIMEOUT) // 连接超时时间
                .option(ChannelOption.SO_KEEPALIVE,true) // TCP保活机制
                .option(ChannelOption.TCP_NODELAY,true) // 关闭Nagle算法
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // 空闲检测
                        // 粘包问题解决
                        ch.pipeline().addLast(new Spliter());
                        // 编解码
                        ch.pipeline().addLast(PacketCodeCHandler.INSTANCE);
                        // 登录
                        ch.pipeline().addLast(new LoginResponseHandler());
                        // 接收消息
                        ch.pipeline().addLast(new MessageResponseHandler());
                        // 创建群聊
                        ch.pipeline().addLast(new CreateGroupResponseHandler());
                        // 加入群聊
                        ch.pipeline().addLast(new JoinGroupResponseHandler());
                        // 退出群聊
                        ch.pipeline().addLast(new QuitGroupResponseHandler());
                        // 获取群成员
                        ch.pipeline().addLast(new ListGroupMembersResponseHandler());
                        // 群消息
                        ch.pipeline().addLast(new GroupMessageResponseHandler());
                        // 退出登录
                        ch.pipeline().addLast(new LogoutResponseHandler());
                        // 心跳包
                    }
                });

        connect(bootstrap,HOST,PORT,MAX_RETIRES);
    }

    /**
     * 连接服务端，如果没有连接上，会自动连接5次
     * @param bootstrap 客户端引导类
     * @param host 服务端主机
     * @param port 服务端端口
     * @param retry 重试次数
     */
    private static void connect(Bootstrap bootstrap, String host, int port, int retry){
        bootstrap.connect(host,port).addListener(future->{
            if(future.isSuccess()){
                // 连接成功
                System.out.println("[IM]连接成功");
                // 启动控制台线程
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
            }else if(retry == 0){
                System.err.println("[IM]重试次数已经用完，连接失败");
            }else{
                int order = (MAX_RETIRES - retry) + 1;
                int delay = 1 << order; // 计算延时
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                // 延时连接
                bootstrap.config().group().schedule(()->connect(bootstrap,host,port,retry-1)
                ,delay, TimeUnit.SECONDS);
            }
        });
    }
    /**
     * 启动控制台线程
     */
    private static void startConsoleThread(Channel channel){
        // 已登录控制台命令执行器
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        // 登陆控制台命令执行器
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner scanner = new Scanner(System.in);
        new Thread(()->{
            while(!Thread.interrupted()){
                if(!SessionUtils.hasLogin(channel)){
                    // 未登录
                    loginConsoleCommand.exec(scanner,channel);
                }else{
                    // 已登录
                    consoleCommandManager.exec(scanner,channel);
                }
            }
        }).start();
    }


}
