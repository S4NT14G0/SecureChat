package com.santiago.securechat.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.santiago.securechat.SecureChatApplication;
import com.santiago.securechat.data.Database;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SecureChatApplicationModule {

    // App Context Injection
    @Provides
    Context provideContext (SecureChatApplication secureChatApplication) { return secureChatApplication.getApplicationContext(); }


    @Provides
    @Singleton
    Database provideDatabase (SecureChatApplication secureChatApplication) {
        return Room.databaseBuilder(secureChatApplication,
                Database.class,
                Database.DATABASE_NAME)
                .build();
    }
}
