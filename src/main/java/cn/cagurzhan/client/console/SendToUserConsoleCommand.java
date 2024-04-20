package cn.cagurzhan.client.console;

import cn.cagurzhan.protocal.request.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 控制台执行器：发送消息给另一个用户
 * @author AjaxZhan
 */
public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("发送消息给某个用户，格式: 对方id+消息：");
        String toUserId = scanner.next();
        String msg = scanner.next();
        channel.writeAndFlush(new MessageRequestPacket(toUserId,msg));
    }
}
