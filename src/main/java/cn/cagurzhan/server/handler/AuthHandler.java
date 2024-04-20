package cn.cagurzhan.server.handler;

import cn.cagurzhan.session.Session;
import cn.cagurzhan.utils.SessionUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 身份验证处理器，继承的是ChannelInboundHandler，表示会对所有类型的包进行拦截
 * @author AjaxZhan
 */
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {
    public static final AuthHandler INSTANCE = new AuthHandler();

    private AuthHandler(){
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(!SessionUtils.hasLogin(ctx.channel())){
            ctx.channel().close();
        }else{
            // 已登录
            ctx.pipeline().remove(this);
            super.channelRead(ctx,msg);
        }
    }
}
