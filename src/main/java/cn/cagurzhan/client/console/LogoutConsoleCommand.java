package cn.cagurzhan.client.console;

import cn.cagurzhan.protocal.request.LogoutRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 退出登录
 * @author AjaxZhan
 */
public class LogoutConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogoutRequestPacket request = new LogoutRequestPacket();
        channel.writeAndFlush(request);
    }
}
