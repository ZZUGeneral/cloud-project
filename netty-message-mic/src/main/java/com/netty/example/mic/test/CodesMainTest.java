package com.netty.example.mic.test;

import com.netty.example.mic.codec.MessageRecordDecoder;
import com.netty.example.mic.codec.MessageRecordEncoder;
import com.netty.example.mic.opcode.OpCode;
import com.netty.example.mic.proto.Header;
import com.netty.example.mic.proto.MessageRecord;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

public class CodesMainTest {
    public static void main(String[] args) throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(new LengthFieldBasedFrameDecoder(1024,9,4,0,0),new LoggingHandler(), new MessageRecordEncoder(), new MessageRecordDecoder());
        Header header = new Header();
        header.setSessionId(123456L);
        header.setType(OpCode.PING.code());
        MessageRecord record = new MessageRecord();
        record.setBody("Hello World!");
        record.setHeader(header);
        channel.writeOutbound(record);

        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageRecordEncoder().encode(null, record, buf);
//        channel.writeInbound(buf);

        //*********模拟半包和粘包问题************//
        //把一个包通过slice拆分成两个部分
        ByteBuf bb1 = buf.slice(0, 7); //获取前面7个字节
        ByteBuf bb2 = buf.slice(7, buf.readableBytes() - 7); //获取后面的字节
        bb1.retain();

        channel.writeInbound(bb1);
        channel.writeInbound(bb2);
    }
}
