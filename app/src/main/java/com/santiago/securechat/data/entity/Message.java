package com.santiago.securechat.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Message {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private int peerId;
    private String body;

    // Getters
    @NonNull
    public int getId() {
        return id;
    }
    public int getPeerId() {
        return peerId;
    }
    public String getBody() {
        return body;
    }

    // Setters

    public void setId(@NonNull int id) {
        this.id = id;
    }
    public void setPeerId(int peerId) {
        this.peerId = peerId;
    }
    public void setBody(String body) {
        this.body = body;
    }
}
