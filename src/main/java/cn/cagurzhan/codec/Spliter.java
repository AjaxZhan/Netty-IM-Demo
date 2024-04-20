package cn.cagurzhan.codec;

import cn.cagurzhan.protocal.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 负责对ByteBuf的拆包并进行协议合法性校验
 * 底层委托PacketCodec实现
 * 此handler有状态：需要维护每个Channel读到的数据。
 * @author AjaxZhan
 */
public class Spliter extends LengthFieldBasedFrameDecoder {
    /**
     * 自定义协议的长度域起始偏移量
     */
    private static final int LENGTH_FIELD_OFFSET = 7;
    /**
     * 长度域的长度
     */
    private static final int LENGTH_FIELD_LENGTH = 4;

    public Spliter(){
        super(Integer.MAX_VALUE,LENGTH_FIELD_OFFSET,LENGTH_FIELD_LENGTH);
    }

    /**
     * 拆包前判断协议是否合法
     */
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if(in.getInt(in.readerIndex())!= PacketCodec.MAGIC_NUMBER){
            // 协议非法
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx,in);
    }
}
