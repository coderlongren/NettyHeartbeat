package com.xys.protocol;

public class ProtocolBean {
    private byte flag;
    private int length;
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
