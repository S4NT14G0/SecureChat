package com.santiago.securechat.data;

import android.arch.persistence.room.RoomDatabase;
import com.santiago.securechat.data.entity.Message;
import com.santiago.securechat.data.entity.Peer;

@android.arch.persistence.room.Database(entities = {Message.class, Peer.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    public static final String DATABASE_NAME = "Database.db";

    private static volatile Database instance;

}
