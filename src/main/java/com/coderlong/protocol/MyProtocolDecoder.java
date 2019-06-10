package com.coderlong.protocol;

import com.coderlong.exception.ProtocolException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname MyProtocolDecoder
 * @Description TODO
 * @Date 19-6-8 下午10:58
 * @Created by coderlong
 */
public class MyProtocolDecoder extends LengthFieldBasedFrameDecoder {
    private static final Logger LOGGER = LoggerFactory.getLogger(LengthFieldBasedFrameDecoder.class);

    public MyProtocolDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);

    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        in = (ByteBuf) super.decode(ctx, in);
        if (in == null) {
            return null;
        }
        //flag
        byte flag = in.readByte();
        //message length
        int length = in.readInt();
        byte[] bytes = null;
        if (in.readableBytes() != length) {
            throw new ProtocolException("mark length do not match real length of message");
        }
        if (length != 0) {
            //read message body
            bytes = new byte[in.readableBytes()];
            in.readBytes(bytes);
        }
        return new ProtocolBean(flag, length, bytes == null ? "" : new String(bytes, "UTF-8"));
    }
}
