package com.santiago.securechat.data;

public class Conversation {

    private int id;
    private long timestampOldestMessage, timestampNewestMessage;
    private String peerIp;
    private int peerPort;

    public Conversation(int id, String peerIp, int peerPort) {
        this.id = id;
        this.peerIp = peerIp;
        this.peerPort = peerPort;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimestampOldestMessage() {
        return timestampOldestMessage;
    }

    public void setTimestampOldestMessage(long timestampOldestMessage) {
        this.timestampOldestMessage = timestampOldestMessage;
    }

    public long getTimestampNewestMessage() {
        return timestampNewestMessage;
    }

    public void setTimestampNewestMessage(long timestampNewestMessage) {
        this.timestampNewestMessage = timestampNewestMessage;
    }

    public String getPeerIp() {
        return peerIp;
    }

    public void setPeerIp(String peerIp) {
        this.peerIp = peerIp;
    }

    public int getPeerPort() {
        return peerPort;
    }

    public void setPeerPort(int peerPort) {
        this.peerPort = peerPort;
    }
}
