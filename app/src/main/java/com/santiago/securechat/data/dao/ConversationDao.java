package com.santiago.securechat.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.santiago.securechat.data.entity.Conversation;
import com.santiago.securechat.data.entity.Peer;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ConversationDao {

    @Insert (onConflict = REPLACE)
    void save (Conversation conversation);

    @Query("SELECT * FROM conversation WHERE conversation.peer == :peer")
    LiveData<Conversation> loadConversation(Peer peer);

    @Query("SELECT * FROM conversation")
    LiveData<List<Conversation>> loadAllConversations();
}
