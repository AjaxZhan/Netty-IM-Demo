package cn.cagurzhan.protocal.request;

import cn.cagurzhan.protocal.Packet;
import cn.cagurzhan.protocal.command.Command;
import lombok.Data;

/**
 * 列出群聊成员
 * @author AjaxZhan
 */
@Data
public class ListGroupMembersRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_REQUEST;
    }
}
