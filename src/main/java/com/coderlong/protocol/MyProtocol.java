package com.coderlong.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.ArrayList;
import java.util.List;

/**
 *  自定义
 */
public class MyProtocol extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte flag = in.readByte();
        int length = in.readInt();
        int contextLen = in.readableBytes();
        if (contextLen > 0) {
            String context = null;
            byte[] bytes = new byte[in.readableBytes()];
            in.writeBytes(bytes);
            context = new String(bytes, "UTF-8");
            out.add(new ProtocolBean(flag, length, context));
        } else {
            out.add(new ProtocolBean(flag, length, ""));
            List<Integer> l = new ArrayList<>();
            List<Integer> l1 = new ArrayList<>();
            l.addAll(l1);
        }
    }
}
