package com.santiago.securechat.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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

}
