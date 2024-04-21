package cn.cagurzhan.server;

import cn.cagurzhan.codec.PacketCodeCHandler;
import cn.cagurzhan.codec.Spliter;
import cn.cagurzhan.handler.IMIdleStateHandler;
import cn.cagurzhan.server.handler.AuthHandler;
import cn.cagurzhan.server.handler.HeartBeatRequestHandler;
import cn.cagurzhan.server.handler.IMHandler;
import cn.cagurzhan.server.handler.LoginRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author AjaxZhan
 */
public class NettyServer {

    private static final int PORT = 8000;

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024) // 连接队列
                .childOption(ChannelOption.SO_KEEPALIVE, true) // TCP保活机制
                .childOption(ChannelOption.TCP_NODELAY, true) // 关闭Nagle算法
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // 空闲检测
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        // 拆包
                        ch.pipeline().addLast(new Spliter());
                        // 编解码
                        ch.pipeline().addLast(PacketCodeCHandler.INSTANCE);
                        // 登录校验
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        // 心跳检测
                        ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
                        // 身份验证
                        ch.pipeline().addLast(AuthHandler.INSTANCE);
                        // IM功能
                        ch.pipeline().addLast(IMHandler.INSTANCE);
                    }
                });

        bind(serverBootstrap,PORT);
    }
    private static void bind(final ServerBootstrap serverBootstrap, final int port){
        serverBootstrap.bind(port).addListener(future -> {
            if(future.isSuccess()){
                System.out.println("端口[" + port + "]，绑定成功");
            }else{
                System.err.println("端口[" + port + "]，绑定成功");
                bind(serverBootstrap,port+1);
            }
        });
    }
}
