package server;

import com.netty.example.mic.codec.MessageRecordDecoder;
import com.netty.example.mic.codec.MessageRecordEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProtocolServer {
    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup();
        //2 用于对接受客户端连接读写操作的线程工作组
        EventLoopGroup work = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(boss, work)    //绑定两个工作线程组
                .channel(NioServerSocketChannel.class)    //设置NIO的模式
                // 初始化绑定服务通道
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline()
                                .addLast(
                                        new LengthFieldBasedFrameDecoder(1024,
                                                9, 4, 0, 0))
                                .addLast(new MessageRecordEncoder())
                                .addLast(new MessageRecordDecoder())
                                .addLast(new ServerHandler());
                    }
                });
        ChannelFuture cf = null;
        try {
            cf = b.bind(8080).sync();
            log.info("ProtocolServer start success");
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            work.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }
}
