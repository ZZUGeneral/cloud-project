package com.yhl.test.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class ReactorClient {
    private static Selector selector;
    public static void main(String[] args) throws IOException {
        selector=Selector.open();
        //创建一个连接通道连接指定的server
        SocketChannel socketChannel= SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("127.0.0.1",8888));
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        while(true){
            selector.select();
            Set<SelectionKey> selectionKeys=selector.selectedKeys();
            Iterator<SelectionKey> iterator=selectionKeys.iterator();
            while(iterator.hasNext()){
                SelectionKey key=iterator.next();
                iterator.remove();
                if(key.isConnectable()){
                    handleConnection(key);
                }else if(key.isReadable()){
                    handleRead(key);
                }
            }
        }
    }
    private static void handleConnection(SelectionKey key) throws IOException {
        SocketChannel socketChannel=(SocketChannel)key.channel();
        if(socketChannel.isConnectionPending()){
            socketChannel.finishConnect();
        }
        socketChannel.configureBlocking(false);
        while(true) {
            Scanner in = new Scanner(System.in);
            String msg = in.nextLine();
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
            socketChannel.register(selector,SelectionKey.OP_READ);
        }
    }
    private static void handleRead(SelectionKey key) throws IOException {
        SocketChannel channel=(SocketChannel)key.channel();
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
        channel.read(byteBuffer);
        System.out.println("client receive msg:"+new String(byteBuffer.array()));
    }
}