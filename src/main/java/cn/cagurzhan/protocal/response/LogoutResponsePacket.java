package cn.cagurzhan.protocal.response;

import cn.cagurzhan.protocal.Packet;
import cn.cagurzhan.protocal.command.Command;
import lombok.Data;

/**
 * 退出登陆的响应数据包
 * @author AjaxZhan
 */
@Data
public class LogoutResponsePacket extends Packet {

    private Boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGOUT_RESPONSE;
    }
}
