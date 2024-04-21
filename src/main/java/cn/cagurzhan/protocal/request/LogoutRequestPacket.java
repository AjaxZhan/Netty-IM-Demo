package cn.cagurzhan.protocal.request;

import cn.cagurzhan.protocal.Packet;
import cn.cagurzhan.protocal.command.Command;
import lombok.Data;

/**
 * 退出登录的请求数据包
 * @author AjaxZhan
 */
@Data
public class LogoutRequestPacket extends Packet {



    @Override
    public Byte getCommand() {
        return Command.LOGOUT_REQUEST;
    }
}
