package com.santiago.securechat.data;

import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.santiago.securechat.data.dao.ConversationDao;
import com.santiago.securechat.data.dao.type_converters.PeerConverter;
import com.santiago.securechat.data.entity.Conversation;
import com.santiago.securechat.data.entity.Message;
import com.santiago.securechat.data.entity.Peer;

@android.arch.persistence.room.Database(entities = {Conversation.class, Message.class, Peer.class}, version = 1, exportSchema = false)
@TypeConverters({PeerConverter.class})
public abstract class Database extends RoomDatabase {
    public static final String DATABASE_NAME = "Database.db";

    private static volatile Database instance;

    public abstract ConversationDao conversationDao();
}
