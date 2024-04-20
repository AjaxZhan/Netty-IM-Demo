package cn.cagurzhan.client.console;

import cn.cagurzhan.protocal.request.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 用户登录
 * @author AjaxZhan
 */
public class LoginConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        System.out.print("请输入用户名: ");
        loginRequestPacket.setUsername(scanner.next());
        System.out.print("请输入密码: ");
        loginRequestPacket.setPassword(scanner.next());

        channel.writeAndFlush(loginRequestPacket);
        waitForResponse();
    }

    /**
     * 等待服务端响应登录请求
     */
    private static void waitForResponse(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
