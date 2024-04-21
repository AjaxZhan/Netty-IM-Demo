package cn.cagurzhan.server.handler;

import cn.cagurzhan.protocal.request.GroupMessageRequestPacket;
import cn.cagurzhan.protocal.response.GroupMessageResponsePacket;
import cn.cagurzhan.utils.SessionUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * 群聊消息请求处理器
 * @author AjaxZhan
 */
@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    private GroupMessageRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket request) throws Exception {
        String toGroupId = request.getToGroupId();
        String message = request.getMessage();

        ChannelGroup channelGroup = SessionUtils.getChannelGroup(toGroupId);

        GroupMessageResponsePacket response = new GroupMessageResponsePacket();
        response.setMessage(message);
        response.setFromGroupId(toGroupId);
        response.setFromUser(SessionUtils.getSession(ctx.channel()));
        channelGroup.writeAndFlush(response);
    }
}
