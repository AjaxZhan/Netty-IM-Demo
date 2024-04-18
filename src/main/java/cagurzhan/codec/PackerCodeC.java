package cn.cagurzhan.codec;

import cn.cagurzhan.protocal.request.LoginRequestPacket;
import cn.cagurzhan.protocal.Packet;
import cn.cagurzhan.serializer.JSONSerializer;
import cn.cagurzhan.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

import static cn.cagurzhan.protocal.command.Command.LOGIN_REQUEST;

/**
 * @author AjaxZhan
 */
public class PackerCodeC {

    private static final int MAGIC_NUMBER = 0x12345678;
    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serializerMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public ByteBuf encode(Packet packet){
        // 适配 io 读写相关的内存，它会尽可能创建一个直接内存
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        byte[] data = Serializer.DEFAULT.serialize(packet);
        // 编码
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(data.length);
        byteBuf.writeBytes(data);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf){
        // 假设传入的已经合法
        byteBuf.skipBytes(4); // magic
        byteBuf.skipBytes(1); // protocol version
        byte serializeAlgorithm = byteBuf.readByte();
        byte command = byteBuf.readByte();
        int length = byteBuf.readInt();

        byte[] data = new byte[length];
        byteBuf.readBytes(data);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if(requestType!= null && serializer!=null){
            return serializer.deserialize(requestType,data);
        }
        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
