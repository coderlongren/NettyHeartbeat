package com.coderlong.bean;

/**
 * @Classname ProtocolBean
 * @Description TODO
 * @Date 19-6-8 下午10:58
 * @Created by coderlong
 */
public class MyProtocolBean {
    //信息标志  0xA 表示心跳包    0xC 表示超时包  0xC 业务信息包
    private byte flag;

    //内容长度
    private int length;

    //内容
    private String content;

    public MyProtocolBean() {
    }

    public MyProtocolBean(byte flag, int length, String content) {
        this.flag = flag;
        this.length = length;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ProtocolBean{" +
                ", flag=" + flag +
                ", length=" + length +
                ", content='" + content + '\'' +
                '}';
    }
}
