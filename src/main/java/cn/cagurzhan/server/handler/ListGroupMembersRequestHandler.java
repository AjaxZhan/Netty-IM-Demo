package cn.cagurzhan.server.handler;

import cn.cagurzhan.protocal.request.ListGroupMembersRequestPacket;
import cn.cagurzhan.protocal.response.ListGroupMembersResponsePacket;
import cn.cagurzhan.session.Session;
import cn.cagurzhan.utils.SessionUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 列出群成员请求处理器
 * @author AjaxZhan
 */
@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {
    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    private ListGroupMembersRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket request) throws Exception {
        ListGroupMembersResponsePacket response = new ListGroupMembersResponsePacket();
        List<Session> sessionList = new ArrayList<>();

        String groupId = request.getGroupId();
        ChannelGroup channelGroup = SessionUtils.getChannelGroup(groupId);
        for (Channel channel : channelGroup) {
            Session session = SessionUtils.getSession(channel);
            if(session!=null){
                sessionList.add(session);
            }
        }

        response.setGroupId(groupId);
        response.setSessionList(sessionList);

        ctx.writeAndFlush(response);
    }
}
