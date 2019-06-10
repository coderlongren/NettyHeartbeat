package com.coderlong.client;

import com.coderlong.common.CustomHeartbeatHandler;
import com.coderlong.protocol.ProtocolBean;
import io.netty.channel.ChannelHandlerContext;

public class ClientHandler extends CustomHeartbeatHandler {
    private Client client;
    public ClientHandler(Client client) {
        super("client");
        this.client = client;
    }

    @Override
    protected void handleData(ChannelHandlerContext channelHandlerContext, ProtocolBean bean) {
        System.out.println(name + " get content: " + bean.getContext());
    }

    /**
     *  作为客户端，关心多久没有和server通信
     * @param ctx
     */
    @Override
    public void handleAllIdle(ChannelHandlerContext ctx) {
        super.handleAllIdle(ctx);
        sendPingMsg(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        // server关闭自己后，尝试重连
        client.doConnect();
    }
}