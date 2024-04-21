package cn.cagurzhan.protocal.response;

import cn.cagurzhan.protocal.Packet;
import cn.cagurzhan.protocal.command.Command;
import lombok.Data;

@Data
public class JoinGroupResponsePacket extends Packet {
    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {

        return Command.JOIN_GROUP_RESPONSE;
    }
}