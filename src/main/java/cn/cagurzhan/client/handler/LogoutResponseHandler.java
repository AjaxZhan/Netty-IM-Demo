package cn.cagurzhan.client.handler;

import cn.cagurzhan.protocal.response.LogoutResponsePacket;
import cn.cagurzhan.utils.SessionUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 退出登录响应处理器
 * @author AjaxZhan
 */
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket msg) throws Exception {
        SessionUtils.unBindSession(ctx.channel());
    }
}
