package com.xys.server;

import com.xys.bean.ProtocolBean;
import com.xys.common.CustomHeartbeatHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.Charset;

public class ServerHandler extends CustomHeart {
    public ServerHandler() {
        super("server");
    }

//    @Override
//    protected void handleData(ChannelHandlerContext channelHandlerContext, ByteBuf buf) {
//        byte[] data = new byte[buf.readableBytes() - 5];
//        ByteBuf responseBuf = Unpooled.copiedBuffer(buf);
//        buf.skipBytes(5);
//        buf.readBytes(data);
//        String content = new String(data);
//        System.out.println(name + " get content: " + content);
//        channelHandlerContext.write(responseBuf);
//    }

    @Override
    protected void handleData(ChannelHandlerContext channelHandlerContext, ProtocolBean protocolBean) {
        System.out.println("sdfsdfsfsdfs");
        System.out.println(name + " get content: " + protocolBean.getContent());
        ByteBuf byteBuf = Unpooled.buffer();
        copyBeanToByteBuf(byteBuf, protocolBean);
        channelHandlerContext.channel().writeAndFlush(byteBuf);
    }

    private ByteBuf copyBeanToByteBuf(ByteBuf byteBuf, ProtocolBean protocolBean) {
        byteBuf.writeByte(protocolBean.getFlag());
        byteBuf.writeInt(protocolBean.getLength());
        byteBuf.writeBytes(protocolBean.getContent().getBytes(Charset.forName("UTF-8")));
        return byteBuf;
    }
    /**
     *  作为server，只关心client多久没有向我发送消息
     * @param ctx
     */
    @Override
    protected void handleReaderIdle(ChannelHandlerContext ctx) {
        super.handleReaderIdle(ctx);
        System.err.println("---client " + ctx.channel().remoteAddress().toString() + " reader timeout, close it---");
        // 主动关闭client
        ctx.close();
    }
}