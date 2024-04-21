package cn.cagurzhan.client.console;

import cn.cagurzhan.protocal.request.QuitGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 退出群聊
 * @author AjaxZhan
 */
public class QuitGroupConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        QuitGroupRequestPacket request = new QuitGroupRequestPacket();
        System.out.print("输入 groupId，退出群聊：");
        String groupId = scanner.next();
        request.setGroupId(groupId);
        channel.writeAndFlush(request);
    }
}
