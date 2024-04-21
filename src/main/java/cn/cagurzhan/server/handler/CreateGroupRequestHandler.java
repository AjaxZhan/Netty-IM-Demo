package cn.cagurzhan.server.handler;

import cn.cagurzhan.protocal.request.CreateGroupRequestPacket;
import cn.cagurzhan.protocal.response.CreateGroupResponsePacket;
import cn.cagurzhan.session.Session;
import cn.cagurzhan.utils.IDUtils;
import cn.cagurzhan.utils.SessionUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建群聊请求数据包
 * @author AjaxZhan
 */
@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();

    private CreateGroupRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket request) throws Exception {

        List<String> usernameList = new ArrayList<>();
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        // 1. 生成groupId
        String groupId = IDUtils.getRandomUserId();

        // 2. 根据userIds 拿到channel，封装为ChannelGroup
        List<String> userIdList = request.getUserIdList();
        for (String userId : userIdList) {
            if(!userId.isEmpty()){
                Channel channel = SessionUtils.getChannel(userId);
                if(channel!= null){
                    Session session = SessionUtils.getSession(channel);
                    channelGroup.add(channel);
                    usernameList.add(session.getUsername());
                }
            }
        }

        // 3. 给【每个客户端】发送拉群请求
        CreateGroupResponsePacket response = new CreateGroupResponsePacket();
        response.setGroupId(groupId);
        response.setSuccess(true);
        response.setUserNameList(usernameList);

        channelGroup.writeAndFlush(response);
        System.out.print("群创建成功，id 为 " + response.getGroupId() + ", ");
        System.out.println("群里面有：" + response.getUserNameList());

        // 4. 绑定groupId 和 ChannelGroup
        SessionUtils.bindChannelGroup(groupId,channelGroup);
    }
}
