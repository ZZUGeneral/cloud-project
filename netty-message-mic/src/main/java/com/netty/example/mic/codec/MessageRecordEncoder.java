package com.netty.example.mic.codec;

import com.netty.example.mic.proto.Header;
import com.netty.example.mic.proto.MessageRecord;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

@Slf4j
public class MessageRecordEncoder extends MessageToByteEncoder<MessageRecord> {
    @Override
    public void encode(ChannelHandlerContext channelHandlerContext, MessageRecord record, ByteBuf byteBuf) throws Exception {
        log.info("===========开始编码Header部分===========");
        Header header = record.getHeader();
        //保存8个字节的sessionId
        byteBuf.writeLong(header.getSessionId());
        //写入1个字节的请求类型
        byteBuf.writeByte(header.getType());

        log.info("===========开始编码Body部分===========");
        Object body = record.getBody();
        if (body != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(body);
            byte[] bytes = bos.toByteArray();
            //写入消息体长度:占4个字节
            byteBuf.writeInt(bytes.length);
            //写入消息体内容
            byteBuf.writeBytes(bytes);
        } else {
            //写入消息长度占4个字节，长度为0
            byteBuf.writeInt(0);
        }
    }
}
