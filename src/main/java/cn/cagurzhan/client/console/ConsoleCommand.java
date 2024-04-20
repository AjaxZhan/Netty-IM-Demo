package cn.cagurzhan.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 控制台操作的抽象接口
 * @author AjaxZhan
 */
public interface ConsoleCommand {

    /**
     * 发送消息
     */
    String SEND_TO_USER = "sendToUser";

    /**
     * 退出登录
     */
    String LOGOUT = "logout";

    /**
     * 创建群聊
     */
    String CREATE_GROUP = "createGroup";

    /**
     * 加入群聊
     */
    String JOIN_GROUP = "joinGroup";

    /**
     * 列出群成员
     */
    String LIST_GROUP_MEMBERS = "listGroupMembers";

    /**
     * 向某个群发消息
     */
    String SEND_TO_GROUP = "sendToGroup";

    /**
     * 退出群聊
     */
    String QUIT_GROUP = "quitGroup";



    void exec(Scanner scanner, Channel channel);
}
