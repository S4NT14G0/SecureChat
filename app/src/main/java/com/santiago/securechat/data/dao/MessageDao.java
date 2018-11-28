package com.santiago.securechat.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.santiago.securechat.data.entity.Message;

import java.util.List;

@Dao
public interface MessageDao {

    @Insert
    void insert(Message message);

    @Update
    void update(Message ... messages);

    @Query("SELECT * FROM message where peerId=:peerId")
    LiveData<List<Message>> findMessagesForPeer (final int peerId);

}
