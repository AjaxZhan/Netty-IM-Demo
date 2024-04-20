package cn.cagurzhan.server.handler;

import cn.cagurzhan.protocal.request.MessageRequestPacket;
import cn.cagurzhan.protocal.response.MessageResponsePacket;
import cn.cagurzhan.session.Session;
import cn.cagurzhan.utils.SessionUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 消息请求处理器
 * @author AjaxZhan
 */
@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    private MessageRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket request) throws Exception {
        long beginTime = System.currentTimeMillis();

        MessageResponsePacket response = new MessageResponsePacket();
        response.setMessage(request.getMessage());
        // 从会话拿到信息
        Session session = SessionUtils.getSession(ctx.channel());
        response.setFromUserId(session.getUserId());
        response.setFromUsername(session.getUsername());

        Channel toChannel = SessionUtils.getChannel(request.getToUserId());
        if(toChannel!= null && SessionUtils.hasLogin(toChannel)){
            toChannel.writeAndFlush(response).addListener(future -> {
                if(future.isDone()){
                    long endTime = System.currentTimeMillis();
                    System.out.println("消息转发处理器-耗时：" + (endTime-beginTime) + "ms");
                }
            });
        }else{
            // 对方未登录
            System.err.println("[" + session.getUserId() + "] 不在线，发送失败!");
        }

    }
}
