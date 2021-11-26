package com.netty.example.reactor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Acceptor implements Runnable{
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    public Acceptor(Selector selector, ServerSocketChannel serverSocketChannel) {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }
    @Override
    public void run() {
        SocketChannel channel;
        try {
            channel=serverSocketChannel.accept();
            System.out.println(channel.getRemoteAddress()+": 收到一个客户端连接");
            channel.configureBlocking(false);
            //当channel连接中数据就绪时，调用DispatchHandler来处理channel
            //巧妙使用了SocketChannel的attach功能，将Hanlder和可能会发生事件的channel链接在一起，当发生事件时，可以立即触发相应链接的Handler。
            channel.register(selector, SelectionKey.OP_READ,new DispatchHandler(channel));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}