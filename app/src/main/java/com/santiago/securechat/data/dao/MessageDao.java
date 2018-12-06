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

import com.santiago.securechat.data.entity.Message;

import java.util.List;

/**
 * Message Data Access Object.  Responsible for Message table modifications in underlying Room SQL database.
 */
@Dao
public interface MessageDao {

    /**
     * Insert a new message into database.
     * @param message - Message
     */
    @Insert
    void insert(Message message);

    /**
     * Update existing message in database.
     * @param messages - Message
     */
    @Update
    void update(Message ... messages);

    /**
     * Find messages that belong to a specific peer.
     * @param peerId - Peer Id
     * @return - Messages sent/received from peer.
     */
    @Query("SELECT * FROM message where peerId=:peerId")
    LiveData<List<Message>> findMessagesForPeer (final int peerId);

}
