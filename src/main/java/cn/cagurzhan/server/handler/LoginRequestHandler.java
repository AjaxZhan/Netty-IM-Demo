package cn.cagurzhan.server.handler;

import cn.cagurzhan.protocal.request.LoginRequestPacket;
import cn.cagurzhan.protocal.response.LoginResponsePacket;
import cn.cagurzhan.session.Session;
import cn.cagurzhan.utils.IDUtils;
import cn.cagurzhan.utils.SessionUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * 登录请求处理器
 * 通过SimpleChannelInboundHandler省略了通过instanceof判断包类型的过程
 * @author AjaxZhan
 */
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    protected LoginRequestHandler(){

    }

    /**
     * 收到登录请求
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        // 构造响应数据包
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion()); // 协议保持一致
        loginResponsePacket.setUsername(loginRequestPacket.getUsername()); // 用户名

        // 判断是否已登录
        if(valid(loginRequestPacket)){
            loginResponsePacket.setSuccess(true);
            // 生成用户ID
            String userId = IDUtils.getRandomUserId();
            loginResponsePacket.setUserId(userId);
            System.out.println("[" + loginRequestPacket.getUsername() + "]登录成功");
            SessionUtils.bindSession(new Session(userId, loginResponsePacket.getUsername()),ctx.channel());
        }else{
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() + ": 登录失败!");
        }

        ctx.writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket){
        return true;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 连接关闭，需要将对应的连接注销
        SessionUtils.unBindSession(ctx.channel());
    }
}
