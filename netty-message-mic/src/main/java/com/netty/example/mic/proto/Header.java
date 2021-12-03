package com.netty.example.mic.proto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Header {
    /**
     * 会话id  : 占8个字节
     */
    private Long sessionId;
    /**
     * 消息类型： 占1个字节
     */
    private Byte type;

    /**
     * 消息长度 : 占4个字节
     */
    private Integer length;
}
