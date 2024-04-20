package cn.cagurzhan.client.console;

import cn.cagurzhan.utils.SessionUtils;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 控制台命令执行器
 * 根据控制台指令执行不同的命令
 * @author AjaxZhan
 */
public class ConsoleCommandManager implements ConsoleCommand {

    /**
     * 存放不同的控制台指令执行器
     */
    private final Map<String,ConsoleCommand> commandMap;

    public ConsoleCommandManager(){
        this.commandMap = new HashMap<>();
        commandMap.put(SEND_TO_USER,new SendToUserConsoleCommand());
    }

    /**
     * 根据指令，调用对应的执行器
     */
    @Override
    public void exec(Scanner scanner, Channel channel) {
        String command = scanner.next();

        if(!SessionUtils.hasLogin(channel)){
            //未登录
            return;
        }

        ConsoleCommand executor = commandMap.get(command);
        if(executor  == null){
            System.err.println("无法识别指令["+ command + "]，请重新输入" );
        }else{
            executor.exec(scanner,channel);
        }
    }
}
