/*
  Santiago Roig
  Faten Haji
  Thien Nguyen

  SecureCh@t
 */

package com.santiago.securechat.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.santiago.securechat.data.entity.Peer;

import java.util.List;

/**
 * Peer Data Access Object.  Responsible for Peer table modifications in underlying Room SQL database.
 */
@Dao
public interface PeerDao {

    @Insert
    long insert(Peer peer);

    @Update
    void update(Peer ... peers);

    @Query("SELECT * FROM peer")
    LiveData<List<Peer>> getPeers ();

    @Query("SELECT * FROM peer WHERE peer.ipAddress == :peerIpAddress")
    Peer findPeerByNetworkId (String peerIpAddress);

    @Query("SELECT * FROM peer where peer.id == :peerId")
    Peer findPeerById (int peerId);

}
