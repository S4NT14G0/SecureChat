package com.santiago.securechat.data;

import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.santiago.securechat.data.dao.MessageDao;
import com.santiago.securechat.data.dao.PeerDao;
import com.santiago.securechat.data.entity.Message;
import com.santiago.securechat.data.entity.Peer;
import com.santiago.securechat.data.entity.type_converter.SecureMessageTypeConverter;

@android.arch.persistence.room.Database(entities = {Message.class, Peer.class}, version = 1, exportSchema = false)
@TypeConverters({SecureMessageTypeConverter.class})
public abstract class Database extends RoomDatabase {
    public static final String DATABASE_NAME = "Database.db";

    private static volatile Database instance;

    public abstract PeerDao peerDao();

    public abstract MessageDao messageDao();

}
