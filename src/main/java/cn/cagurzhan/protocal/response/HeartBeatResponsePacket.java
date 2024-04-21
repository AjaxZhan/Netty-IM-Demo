package cn.cagurzhan.protocal.response;

import cn.cagurzhan.protocal.Packet;
import cn.cagurzhan.protocal.command.Command;

/**
 * 心跳响应数据包
 * @author AjaxZhan
 */
public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_RESPONSE;
    }
}
