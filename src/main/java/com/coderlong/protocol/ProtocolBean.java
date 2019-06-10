package com.coderlong.protocol;

public class ProtocolBean {
    /**
     * 心跳标识， 1 PING, 2 PONG, 3 MSG
     */
    private byte flag;
    /**
     *  message length
     */
    private int length;
    /**
     *  message
     */
    private String context;

    public ProtocolBean() {
    }

    public byte getFlag() {
        return flag;
    }

    public void setFlag(byte flag) {
        this.flag = flag;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public ProtocolBean(byte flag, int length, String context) {
        this.flag = flag;
        this.length = length;
        this.context = context;
    }
}
