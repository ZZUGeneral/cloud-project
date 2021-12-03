package com.netty.example.mic.opcode;

public enum OpCode {
    BUSI_REQ((byte) 0),
    BUSI_RESP((byte) 1),
    PING((byte) 3),
    PONG((byte) 4);

    private Byte code;

    private OpCode(byte code) {
        this.code = code;
    }

    public byte code() {
        return code;
    }
}
