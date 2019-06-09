package com.xys.protocol;

import com.xys.bean.ProtocolBean;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @Classname MyProtocolDecoder
 * @Description TODO
 * @Date 19-6-8 下午10:58
 * @Created by coderlong
 */
public class MyProtocolDecoder extends LengthFieldBasedFrameDecoder {
    public MyProtocolDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);

    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        in = (ByteBuf) super.decode(ctx, in);

        if (in == null) {
            return null;
        }
        //读取flag字段
        byte flag = in.readByte();
        //读取length字段
        int length = in.readInt();
        System.out.println("flag : " + flag + "len :" + length + "len :" + in.readableBytes());
//        if (in.readableBytes() != length) {
//            throw new Exception("标记的长度不符合实际长度");
//        }
        byte[] bytes = null;
        if (length != 0) {
            //读取body
            bytes = new byte[in.readableBytes()];
            in.readBytes(bytes);
        }
        return new ProtocolBean(flag, length, bytes == null ? "" : new String(bytes, "UTF-8"));
    }
}
