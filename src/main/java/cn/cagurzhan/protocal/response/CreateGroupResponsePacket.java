package cn.cagurzhan.protocal.response;

import cn.cagurzhan.protocal.Packet;
import cn.cagurzhan.protocal.command.Command;
import lombok.Data;

import java.util.List;

/**
 * 创建群聊响应数据包
 * @author AjaxZhan
 */
@Data
public class CreateGroupResponsePacket extends Packet {

    private Boolean success;

    private String groupId;

    private List<String> userNameList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
