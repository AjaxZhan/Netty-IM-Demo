package cn.cagurzhan.protocal.response;

import cn.cagurzhan.protocal.Packet;
import cn.cagurzhan.protocal.command.Command;
import cn.cagurzhan.session.Session;
import lombok.Data;

import java.util.List;

@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {

        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}