package com.santiago.securechat.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.santiago.securechat.comm.SecureChatServer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    @Provides
    Gson provideGson () {
        return new GsonBuilder().create();
    }

    @Provides
    Executor provideExecutor () { return Executors.newSingleThreadExecutor(); }

    @Provides
    @Singleton
    SecureChatServer provideSecureChatServer (Executor executor) {return new SecureChatServer(executor);}

}
