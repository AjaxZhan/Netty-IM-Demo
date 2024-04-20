package cn.cagurzhan.protocal.response;

import cn.cagurzhan.protocal.Packet;
import cn.cagurzhan.protocal.command.Command;
import lombok.Data;

/**
 * 登录响应
 * @author AjaxZhan
 */
@Data
public class LoginResponsePacket extends Packet {

    private String userId;
    private String username;
    private Boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
