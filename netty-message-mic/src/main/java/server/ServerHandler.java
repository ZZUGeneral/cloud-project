package server;

import com.netty.example.mic.opcode.OpCode;
import com.netty.example.mic.proto.Header;
import com.netty.example.mic.proto.MessageRecord;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageRecord messageRecord = (MessageRecord) msg;
        log.info("server receive message:" + messageRecord);
        MessageRecord res = new MessageRecord();
        Header header = new Header();
        header.setSessionId(messageRecord.getHeader().getSessionId());
        header.setType(OpCode.BUSI_RESP.code());
        String message = "Server Response Message!";
        res.setBody(message);
        header.setLength(message.length());
        ctx.writeAndFlush(res);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("服务器读取数据异常");
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}