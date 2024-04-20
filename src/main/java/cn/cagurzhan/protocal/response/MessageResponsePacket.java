package cn.cagurzhan.protocal.response;

import cn.cagurzhan.protocal.Packet;
import cn.cagurzhan.protocal.command.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息响应数据包
 * @author AjaxZhan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponsePacket extends Packet {
    private String fromUserId;
    private String fromUsername;
    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
