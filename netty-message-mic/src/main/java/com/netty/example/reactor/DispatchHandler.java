package com.netty.example.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class DispatchHandler implements Runnable{
    private SocketChannel channel;
    public DispatchHandler(SocketChannel channel) {
        this.channel = channel;
    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"---handler"); //case: 打印当前线程名称，证明I/O是同一个线程来处理。
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        int len=0,total=0;
        String msg="";
        try {
            do {
                len = channel.read(buffer);
                if (len > 0) {
                    total += len;
                    msg += new String(buffer.array());
                }
                buffer.clear();
            } while (len > buffer.capacity());
            System.out.println(channel.getRemoteAddress()+":Server Receive msg:"+msg);
        }catch (Exception e){
            e.printStackTrace();
            if(channel!=null){
                try {
                    channel.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }
}