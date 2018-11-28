package com.santiago.securechat.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.santiago.securechat.SecureChatApplication;
import com.santiago.securechat.data.Database;
import com.santiago.securechat.data.dao.MessageDao;
import com.santiago.securechat.data.dao.PeerDao;
import com.santiago.securechat.data.repository.ConversationRepository;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module (includes = ViewModelModule.class)
public class SecureChatApplicationModule {

    // App Context Injection
    @Provides
    Context provideContext (SecureChatApplication secureChatApplication) { return secureChatApplication.getApplicationContext(); }


    @Provides
    @Singleton
    Database provideDatabase (Application application) {
        return Room.databaseBuilder(application,
                Database.class,
                Database.DATABASE_NAME)
                .build();
    }

    @Provides
    @Singleton
    PeerDao providePeerDao(Database database) { return database.peerDao(); }

    @Provides
    @Singleton
    MessageDao provideMessageDao(Database database) { return database.messageDao(); }

    @Provides
    @Singleton
    ConversationRepository provideConversationRepository (PeerDao peerDao, MessageDao messageDao, Executor executor) {
        return new ConversationRepository(peerDao, messageDao, executor);
    }
}
