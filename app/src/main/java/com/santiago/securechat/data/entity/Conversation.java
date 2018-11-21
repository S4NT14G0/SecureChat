package com.santiago.securechat.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Conversation {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private long timestampOldestMessage, timestampNewestMessage;
    private String peerIp;
    private int peerPort;

    public Conversation(String peerIp, int peerPort) {
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
