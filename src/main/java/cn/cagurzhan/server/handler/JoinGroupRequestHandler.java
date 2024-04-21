package cn.cagurzhan.server.handler;

import cn.cagurzhan.protocal.request.JoinGroupRequestPacket;
import cn.cagurzhan.protocal.response.JoinGroupResponsePacket;
import cn.cagurzhan.utils.SessionUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * 加入群聊请求处理
 * @author AjaxZhan
 */
@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();

    private JoinGroupRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket requestPacket) throws Exception {
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtils.getChannelGroup(groupId);
        channelGroup.add(ctx.channel());

        JoinGroupResponsePacket response = new JoinGroupResponsePacket();
        response.setGroupId(groupId);
        response.setSuccess(true);
        ctx.writeAndFlush(response);
    }
}
