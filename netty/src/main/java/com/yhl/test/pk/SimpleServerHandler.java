package com.yhl.test.pk;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.UUID;

public class SimpleServerHandler extends ChannelInboundHandlerAdapter {
    private int count;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        byte[] buffer = new byte[in.readableBytes()]; //长度为可读的字节数
        in.readBytes(buffer); //读取到字节数组中
        String message = new String(buffer, "UTF-8");
        System.out.println("服务端收到的消息内容：" + message + "\n服务端收到的消息数量" + (++count));
        ByteBuf resBB = Unpooled.copiedBuffer(UUID.randomUUID().toString(), Charset.forName("utf-8"));
        ctx.writeAndFlush(resBB);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();//关闭连接
    }
}
