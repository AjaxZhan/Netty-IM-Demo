package cn.cagurzhan.server.handler;

import cn.cagurzhan.protocal.request.QuitGroupRequestPacket;
import cn.cagurzhan.protocal.response.QuitGroupResponsePacket;
import cn.cagurzhan.utils.SessionUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * 退出群聊请求处理器
 * @author AjaxZhan
 */
@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {

    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();
    private QuitGroupRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket request) throws Exception {
        QuitGroupResponsePacket response = new QuitGroupResponsePacket();

        ChannelGroup channelGroup = SessionUtils.getChannelGroup(request.getGroupId());
        channelGroup.remove(ctx.channel());

        response.setSuccess(true);
        response.setGroupId(request.getGroupId());
        ctx.writeAndFlush(response);
    }
}
