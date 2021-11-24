package com.netty.example.pk;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

public class SimpleClientHandler extends ChannelInboundHandlerAdapter {

    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端和服务端成功建立连接");
        //客户端和服务端建立连接后，发送十次消息给服务端
        for (int i = 0; i < 10; i++) {
            ByteBuf buf = Unpooled.copiedBuffer("客户端消息" + i, Charset.forName("utf-8"));
            ctx.writeAndFlush(buf);
        }
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接收服务端发过来的消息
        System.out.println("接收到服务端返回的信息");
        ByteBuf buf = (ByteBuf) msg;
        byte[] buffer = new byte[buf.readableBytes()];
        buf.readBytes(buffer);
        String message = new String(buffer, Charset.forName("utf-8"));
        System.out.println("客户端收到的消息内容为：" + message);
        System.out.println("客户端收到的消息数量为：" + (++count));
        super.channelRead(ctx, msg);
    }
}