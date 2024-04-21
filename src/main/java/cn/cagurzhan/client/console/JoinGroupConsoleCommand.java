package cn.cagurzhan.client.console;

import cn.cagurzhan.protocal.request.JoinGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 添加群聊
 * @author AjaxZhan
 */
public class JoinGroupConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("输入 groupId，加入群聊：");
        String groupId = scanner.next();

        JoinGroupRequestPacket request = new JoinGroupRequestPacket();
        request.setGroupId(groupId);

        channel.writeAndFlush(request);
    }
}
