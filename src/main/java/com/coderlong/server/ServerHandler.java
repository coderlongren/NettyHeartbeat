package com.coderlong.server;

import com.coderlong.common.CustomHeartbeatHandler;
import com.coderlong.protocol.ProtocolBean;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends CustomHeartbeatHandler {
    public ServerHandler() {
        super("server");
    }

    @Override
    protected void handleData(ChannelHandlerContext channelHandlerContext, ProtocolBean bean) {
        ByteBuf byteBuf = Unpooled.buffer();
        writeToClient(byteBuf, bean);
        System.out.println(name + " get content: " + bean.getContext());
        channelHandlerContext.writeAndFlush(byteBuf);
    }

    private void writeToClient(ByteBuf byteBuf, ProtocolBean bean) {
        byteBuf.writeByte(bean.getFlag());
        byteBuf.writeInt(bean.getLength());
        byteBuf.writeBytes(bean.getContext().getBytes());
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