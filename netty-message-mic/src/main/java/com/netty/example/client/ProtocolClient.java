package com.netty.example.client;

import com.netty.example.mic.codec.MessageRecordDecoder;
import com.netty.example.mic.codec.MessageRecordEncoder;
import com.netty.example.mic.opcode.OpCode;
import com.netty.example.mic.proto.Header;
import com.netty.example.mic.proto.MessageRecord;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.net.InetSocketAddress;

public class ProtocolClient {
    public static void main(String[] args) {
        //创建工作线程
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(
                                        new LengthFieldBasedFrameDecoder(1024,
                                                9, 4, 0, 0))
                                .addLast(new MessageRecordEncoder())
                                .addLast(new MessageRecordDecoder())
                                .addLast(new ClientHandler());
                    }
                });
        // 发起异步连接操作
        try {
            ChannelFuture future = b.connect(new InetSocketAddress("localhost", 8080)).sync();
            Channel c = future.channel();
            for (int i = 0; i < 500; i++) {
                MessageRecord message = new MessageRecord();
                Header header = new Header();
                header.setSessionId(10001L);
                header.setType(OpCode.BUSI_REQ.code());
                message.setHeader(header);
                String context = "我是请求数据：" + i;
                System.out.println(context);
                header.setLength(context.length());
                message.setBody(context);
                c.writeAndFlush(message);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }


    }
}
