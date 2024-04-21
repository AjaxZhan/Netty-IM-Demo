package cn.cagurzhan.client.console;

import cn.cagurzhan.protocal.request.ListGroupMembersRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 列出群成员
 * @author AjaxZhan
 */
public class ListGroupMembersConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        ListGroupMembersRequestPacket request = new ListGroupMembersRequestPacket();
        System.out.print("输入 groupId，获取群成员列表：");
        String groupId = scanner.next();
        request.setGroupId(groupId);

        channel.writeAndFlush(request);
    }
}
