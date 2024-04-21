package cn.cagurzhan.client.handler;

import cn.cagurzhan.protocal.request.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * @author AjaxZhan
 */
public class HeartBeatResponseHandler extends ChannelInboundHandlerAdapter {

    private static final int HEARTBEAT_INTERVAL = 5;


    /**
     * 连接成功回调，定时发送心跳包
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartBeat(ctx);
        super.channelActive(ctx);
    }

    private void scheduleSendHeartBeat(ChannelHandlerContext ctx){
        ctx.executor().schedule(()->{
            if(ctx.channel().isActive()){
                HeartBeatRequestPacket request = new HeartBeatRequestPacket();
                ctx.channel().writeAndFlush(request);
            }
        },HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }
}
