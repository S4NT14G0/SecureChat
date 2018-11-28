package com.santiago.securechat.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.santiago.securechat.data.entity.Peer;

import java.util.List;

@Dao
public interface PeerDao {

    @Insert
    void insert(Peer peer);

    @Update
    void update(Peer ... peers);

    @Query("SELECT * FROM peer")
    LiveData<List<Peer>> getPeers ();

    @Query("SELECT * FROM peer WHERE peer.ipAddress == :peerIpAddress AND peer.port == :peerPort")
    Peer findPeerByNetworkId (String peerIpAddress, int peerPort);

}
