package cn.cagurzhan.server.handler;

import cn.cagurzhan.protocal.request.LogoutRequestPacket;
import cn.cagurzhan.protocal.response.LogoutResponsePacket;
import cn.cagurzhan.session.Session;
import cn.cagurzhan.utils.SessionUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 退出请求的处理器
 * @author AjaxZhan
 */
@ChannelHandler.Sharable
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {

    public static final LogoutRequestHandler INSTANCE = new LogoutRequestHandler();

    private LogoutRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket msg) throws Exception {
        // 移除Session
        SessionUtils.unBindSession(ctx.channel());
        LogoutResponsePacket response = new LogoutResponsePacket();
        response.setSuccess(true);
        ctx.channel().writeAndFlush(response);
    }
}
