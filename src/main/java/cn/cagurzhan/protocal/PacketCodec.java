package cn.cagurzhan.protocal;

import cn.cagurzhan.protocal.command.Command;
import cn.cagurzhan.protocal.request.*;
import cn.cagurzhan.protocal.response.*;
import cn.cagurzhan.serializer.JSONSerializer;
import cn.cagurzhan.serializer.Serializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据包编解码器
 * 负责根据自定义协议实现数据包 <-> ByteBuf
 * @author AjaxZhan
 */
public class PacketCodec {
    /**
     * 魔数，用于检验数据包是否合法
     */
    public static final int MAGIC_NUMBER = 0x12345678;

    /**
     * 无状态，故用单例模式即可
     */
    public static final PacketCodec INSTANCE = new PacketCodec();

    /**
     * 数据包类型
     */
    private final Map<Byte, Class<? extends Packet>> packetTypeMap;

    /**
     * 序列化器选择
     */
    private final Map<Byte, Serializer> serializerMap;

    private PacketCodec(){
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);
        packetTypeMap.put(Command.LOGOUT_REQUEST, LogoutRequestPacket.class);
        packetTypeMap.put(Command.LOGOUT_RESPONSE, LogoutResponsePacket.class);
        packetTypeMap.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        packetTypeMap.put(Command.CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        packetTypeMap.put(Command.JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
        packetTypeMap.put(Command.JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
        packetTypeMap.put(Command.QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
        packetTypeMap.put(Command.QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
        packetTypeMap.put(Command.LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
        packetTypeMap.put(Command.LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);
        packetTypeMap.put(Command.GROUP_MESSAGE_REQUEST, GroupMessageRequestPacket.class);
        packetTypeMap.put(Command.GROUP_MESSAGE_RESPONSE, GroupMessageResponsePacket.class);
        packetTypeMap.put(Command.HEARTBEAT_REQUEST, HeartBeatRequestPacket.class);
        packetTypeMap.put(Command.HEARTBEAT_RESPONSE, HeartBeatResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(),serializer);
    }

    /**
     * 编码为ByteBuf
     * @param byteBuf 从编码的handler传入的ByteBuf，ByteBuf的分配和回收委托给对应的handler
     * @param packet 数据包
     */
    public void encode(ByteBuf byteBuf, Packet packet){
        // 之前写法：直接承担ByteBuf的分配和回收任务
        // ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        // byte[] data = Serializer.DEFAULT.serialize(packet);

        byte[] buffer = Serializer.DEFAULT.serialize(packet);
        // 编码
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(buffer.length);
        byteBuf.writeBytes(buffer);
    }

    /**
     * 解码为数据包，数据的合法性已委托给handler拦截，这里只需要负责反序列化出Packet
     */
    public Packet decode(ByteBuf byteBuf){
        // 跳过魔数
        byteBuf.skipBytes(4);
        // 跳过版本号
        byteBuf.skipBytes(1);
        // 读取序列化算法
        byte serializeAlgorithm = byteBuf.readByte();
        // 读取命令
        byte command = byteBuf.readByte();
        // 读取数据包长度
        int length = byteBuf.readInt();
        // 读取数据包
        byte[] data = new byte[length];
        byteBuf.readBytes(data);

        // 根据命令，创建对应的数据包
        Class<? extends Packet> requestType = getRequestType(command);
        // 根据序列化算法，实例化对应的序列化器
        Serializer serializer = getSerializer(serializeAlgorithm);

        // 反序列化
        if(requestType!= null && serializer!=null){
            return serializer.deserialize(requestType,data);
        }
        return null;
    }

    /**
     * 根据序列化算法拿到序列化器
     */
    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

    /**
     * 根据命令类型拿到对应数据包类型
     */
    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
