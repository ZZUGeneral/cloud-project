package com.netty.example.reactor.multi;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MultiDispatchHandler implements Runnable{
    private SocketChannel channel;
    public MultiDispatchHandler(SocketChannel channel) {
        this.channel = channel;
    }
    private static Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() << 1);
    @Override
    public void run() {
        processor();
    }
    private void processor(){
        executor.execute(new ReaderHandler(channel));
    }
    public static class ReaderHandler implements Runnable{
        private SocketChannel channel;
        public ReaderHandler(SocketChannel socketChannel) {
            this.channel = socketChannel;
        }
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"---handler"); //case: 打印当前线程名称，证明I/O是同一个线程来处理。
            ByteBuffer buffer= ByteBuffer.allocate(1024);
            int len=0;
            String msg="";
            try {
                do {
                    len = channel.read(buffer);
                    if (len > 0) {
                        msg += new String(buffer.array());
                    }
                    buffer.clear();
                } while (len > buffer.capacity());
                if(len>0) {
                    System.out.println(channel.getRemoteAddress() + ":Server Receive msg:" + msg);
                }
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
}