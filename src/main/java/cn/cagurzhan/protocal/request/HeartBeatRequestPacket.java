package cn.cagurzhan.protocal.request;

import cn.cagurzhan.protocal.Packet;
import cn.cagurzhan.protocal.command.Command;

/**
 * 心跳包
 * @author AjaxZhan
 */
public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_REQUEST;
    }
}
