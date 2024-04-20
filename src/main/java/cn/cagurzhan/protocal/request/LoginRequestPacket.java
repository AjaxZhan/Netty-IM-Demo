package cn.cagurzhan.protocal.request;

import cn.cagurzhan.protocal.command.Command;
import cn.cagurzhan.protocal.Packet;
import lombok.Data;

/**
 * @author AjaxZhan
 */
@Data
public class LoginRequestPacket extends Packet {

    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
