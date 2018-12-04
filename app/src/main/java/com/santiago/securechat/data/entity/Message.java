package com.santiago.securechat.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(indices = {@Index("messageId")},
        foreignKeys = @ForeignKey(entity = Peer.class,
        parentColumns = "id",
        childColumns = "peerId",
        onDelete = CASCADE))
public class Message {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "messageId")
    @NonNull
    private int messageId;
    @ColumnInfo(name = "peerId")
    private int peerId;
    private String body;
    private boolean sendSuccessful;
    private boolean isOutgoingMessage;

    // Getters
    @NonNull
    public int getMessageId() {
        return messageId;
    }
    public int getPeerId() {
        return peerId;
    }
    public String getBody() {
        return body;
    }
    public boolean isSendSuccessful() {
        return sendSuccessful;
    }
    public boolean isOutgoingMessage() {
        return isOutgoingMessage;
    }

    // Setters
    public void setMessageId(@NonNull int messageId) {
        this.messageId = messageId;
    }
    public void setPeerId(int peerId) {
        this.peerId = peerId;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public void setSendSuccessful (boolean sendSuccessful) {this.sendSuccessful = sendSuccessful;}
    public void setOutgoingMessage (boolean isOutgoingMessage) {this.isOutgoingMessage = isOutgoingMessage;}
}
