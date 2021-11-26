package com.netty.example.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyBasicServerExample {
    public void bind(int port) {
// 我们要创建两个EventLoopGroup，
        // 一个是boss专门用来接收连接，可以理解为处理accept事件，
        // 另一个是worker，可以关注除了accept之外的其它事件，处理子任务。
        //上面注意，boss线程一般设置一个线程，设置多个也只会用到一个，而且多个目前没有应用场景，
        // worker线程通常要根据服务器调优，如果不写默认就是cpu的两倍。
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //服务端要启动，需要创建ServerBootStrap，
            // 在这里面netty把nio的模板式的代码都给封装好了
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup) //配置boss和worker线程
                    //配置Server的通道，相当于NIO中的ServerSocketChannel
                    .channel(NioServerSocketChannel.class)
                    //childHandler表示给worker那些线程配置了一个处理器，
                    // 配置初始化channel，也就是给worker线程配置对应的handler，当收到客户端的请求时，分配给指定的handler处理
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NormalMessageHandler()); //添加handler，也就是具体的IO事件处理器
                        }
                    });
            //由于默认情况下是NIO异步非阻塞，所以绑定端口后，通过sync()方法阻塞直到连接建立
            //绑定端口并同步等待客户端连接（sync方法会阻塞，直到整个启动过程完成）
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            System.out.println("Netty Server Started,Listening on :" + port);
            //等待服务端监听端口关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyBasicServerExample().bind(8080);
    }
}
