package com.xys.client;

import com.xys.common.CustomHeartbeatHandler;
import com.xys.protocol.MyProtocolDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Client {
    private NioEventLoopGroup workGroup = new NioEventLoopGroup(4);
    private Channel channel;
    private Bootstrap bootstrap;
    private final int MAX_CONNECT_COUNT = 10;
    private volatile int connectCount = 0;

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.start();
        client.sendData();
    }

    public void sendData() throws Exception {
        for (int i = 0; i < 10; i++) {
            String content = "client msg  " + i;
            // 这里是分配的应用层的包大小
            ByteBuf buf = channel.alloc().buffer(5 + content.getBytes().length);
            buf.writeByte(CustomHeartbeatHandler.CUSTOM_MSG);
            buf.writeInt(content.getBytes().length);
            buf.writeBytes(content.getBytes(Charset.forName("UTF-8")));
            channel.writeAndFlush(buf);

            Thread.sleep(1000);
        }
    }

    public void start() {
        try {
            bootstrap = new Bootstrap();
            bootstrap
                    .group(workGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline p = socketChannel.pipeline();
                            p.addLast(new IdleStateHandler(0, 0, 5));
//                            p.addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 1, 0));
                            p.addLast(new MyProtocolDecoder(1024, 1, 4, 0, 0, false));
                            p.addLast(new ClientHandler(Client.this));
                        }
                    });
            doConnect();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void doConnect() {
        // 最大重试次数
        if (connectCount > MAX_CONNECT_COUNT) {
            return;
        }
        if (channel != null && channel.isActive()) {
            return;
        }

        ChannelFuture future = bootstrap.connect("127.0.0.1", 12345);

        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture futureListener) throws Exception {
                if (futureListener.isSuccess()) {
                    channel = futureListener.channel();
                    System.out.println("Connect to server successfully!");
                } else {
                    System.out.println("Failed to connect to server, try connect after 10s");

                    futureListener.channel().eventLoop().schedule(new Runnable() {
                        @Override
                        public void run() {
                            doConnect();
                        }
                    }, 10, TimeUnit.SECONDS);
                }
            }
        });
    }

}
