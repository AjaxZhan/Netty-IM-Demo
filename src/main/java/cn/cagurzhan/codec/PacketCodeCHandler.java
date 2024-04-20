package cn.cagurzhan.codec;

import cn.cagurzhan.protocal.Packet;
import cn.cagurzhan.protocal.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * 实现ByteBuf和数据包互相转化的编解码器
 * @author AjaxZhan
 */
@ChannelHandler.Sharable
public class PacketCodeCHandler extends MessageToMessageCodec<ByteBuf, Packet> {

    public static final PacketCodeCHandler INSTANCE = new PacketCodeCHandler();

    private PacketCodeCHandler(){

    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, List<Object> out) throws Exception {
        // 分配ByteBuf
        // TODO 何时回收这块内存？
        ByteBuf byteBuf = ctx.alloc().ioBuffer();
        PacketCodec.INSTANCE.encode(byteBuf,packet);
        out.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        out.add(PacketCodec.INSTANCE.decode(byteBuf));
    }
}
