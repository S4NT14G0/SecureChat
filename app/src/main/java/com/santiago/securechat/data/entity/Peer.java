package com.santiago.securechat.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(indices = {@Index("id")})
public class Peer {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    private int id;
    private String ipAddress;
    private int port;
    private boolean isBlackListed;

    public Peer (String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

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
    public void setId(@NonNull int id) {
        this.id = id;
    }
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public void setBlackListed(boolean blackListed) {
        isBlackListed = blackListed;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Peer))
            return false;

        Peer peer = (Peer) obj;

        return peer.getPort() == this.getPort() && peer.getIpAddress().equals(this.getIpAddress());
    }
}
