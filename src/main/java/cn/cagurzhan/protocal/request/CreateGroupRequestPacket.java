package cn.cagurzhan.protocal.request;

import cn.cagurzhan.protocal.Packet;
import cn.cagurzhan.protocal.command.Command;
import lombok.Data;

import java.util.List;

/**
 * 创建群组请求数据包
 * @author AjaxZhan
 */
@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
