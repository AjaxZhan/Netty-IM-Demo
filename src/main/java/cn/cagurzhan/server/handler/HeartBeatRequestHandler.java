package cn.cagurzhan.server.handler;

import cn.cagurzhan.client.handler.HeartBeatResponseHandler;
import cn.cagurzhan.protocal.request.HeartBeatRequestPacket;
import cn.cagurzhan.protocal.response.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author AjaxZhan
 */
@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {
    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

    private HeartBeatRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket msg) throws Exception {
        // 不绕过后续处理链
        ctx.writeAndFlush(new HeartBeatResponsePacket());
    }
}
