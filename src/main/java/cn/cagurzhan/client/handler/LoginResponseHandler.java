package cn.cagurzhan.client.handler;

import cn.cagurzhan.protocal.Packet;
import cn.cagurzhan.protocal.command.Command;
import cn.cagurzhan.protocal.request.LoginRequestPacket;
import cn.cagurzhan.protocal.response.LoginResponsePacket;
import cn.cagurzhan.session.Session;
import cn.cagurzhan.utils.SessionUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * 登录响应处理器
 * @author AjaxZhan
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        String userId = loginResponsePacket.getUserId();
        String username = loginResponsePacket.getUsername();
        // 判断登录
        if(loginResponsePacket.getSuccess()){
            System.out.println("[" + username + "]登录成功，userId 为: " + userId);
            SessionUtils.bindSession(new Session(userId,username),ctx.channel());
        }else{
            System.out.println("[" + username + "]登录失败，原因：" + loginResponsePacket.getReason());
        }
    }
}
