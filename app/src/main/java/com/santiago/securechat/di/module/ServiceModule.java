/*
  Santiago Roig
  Faten Haji
  Thien Nguyen

  SecureCh@t
 */


package com.santiago.securechat.di.module;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.santiago.securechat.SecureChatApplication;
import com.santiago.securechat.comm.SecureChatClient;
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
    SecureChatServer provideSecureChatServer (Executor executor, Application application) {return new SecureChatServer(executor, application);}

    @Provides
    @Singleton
    SecureChatClient provideSecureChatClient (Executor executor, Application application) {return new SecureChatClient(executor, application);}
}
