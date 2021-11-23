package com.yhl.test.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Reactor implements Runnable{
    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;
    public Reactor(int port) throws IOException {
        //创建选择器
        selector= Selector.open();
        //创建NIO-Server
        serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        SelectionKey key=serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 绑定一个附加对象
        key.attach(new Acceptor(selector,serverSocketChannel));
    }
    @Override
    public void run() {
        while(!Thread.interrupted()){
            try {
                selector.select(); //阻塞等待就绪事件
                Set selectionKeys=selector.selectedKeys();
                Iterator it=selectionKeys.iterator();
                while(it.hasNext()){
                    dispatch((SelectionKey) it.next());
                    it.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void dispatch(SelectionKey key){
        //调用之前注册时附加的对象，也就是attach附加的acceptor
        Runnable r=(Runnable)key.attachment();
        if(r!=null){
            r.run();
        }
    }
    public static void main(String[] args) throws IOException {
        new Thread(new Reactor(8888)).start();
    }
}
