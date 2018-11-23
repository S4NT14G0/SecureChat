package com.santiago.securechat.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class peer {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String ipAddress;
    private int port;
    private boolean isBlackListed;

    // Getters
    @NonNull
    public int getId() {
        return id;
    }
    public String getIpAddress() {
        return ipAddress;
    }
    public int getPort() {
        return port;
    }
    public boolean isBlackListed() {
        return isBlackListed;
    }

    // Setters
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public void setBlackListed(boolean blackListed) {
        isBlackListed = blackListed;
    }
}
