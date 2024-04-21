package cn.cagurzhan.client.console;

import cn.cagurzhan.protocal.request.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 创建群组
 * @author AjaxZhan
 */
public class CreateGroupConsoleCommand implements ConsoleCommand{

    public static final String USER_IDS_SPLIT = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreateGroupRequestPacket request = new CreateGroupRequestPacket();
        System.out.print("【拉人群聊】输入 userId 列表，userId 之间英文逗号隔开：");
        String userIds = scanner.next();
        request.setUserIdList(Arrays.asList(userIds.split(USER_IDS_SPLIT)));
        channel.writeAndFlush(request);
    }
}
