package com.yhl.test.pk;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringEncoder;

public class LengthFieldBasedFrameDecoderClient {
    public static void main(String[] args) {
        EventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(workGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                //如果协议中的第一个字段为长度字段，
                                // netty提供了LengthFieldPrepender编码器，
                                // 它可以计算当前待发送消息的二进制字节长度，将该长度添加到ByteBuf的缓冲区头中
                                .addLast(new LengthFieldPrepender(2, 0, false))
                                //使用StringEncoder，在通过writeAndFlush时，不需要自己转化成ByteBuf
                                //StringEncoder会自动做这个事情
                                .addLast(new StringEncoder())
                                .addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                        ctx.writeAndFlush("i am request!");
                                        ctx.writeAndFlush("i am a another request!");
                                    }
                                });
                    }
                });
        try {
            ChannelFuture channelFuture = b.connect("localhost", 8080).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
        }
    }
}
