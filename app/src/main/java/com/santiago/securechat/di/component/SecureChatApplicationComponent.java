package com.santiago.securechat.di.component;

import android.app.Application;

import com.santiago.securechat.SecureChatApplication;
import com.santiago.securechat.di.module.SecureChatApplicationModule;
import com.santiago.securechat.di.module.ServiceModule;
import com.santiago.securechat.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component (modules = {AndroidSupportInjectionModule.class, SecureChatApplicationModule.class, ViewModelModule.class, ServiceModule.class})
public interface SecureChatApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application (Application application);
        SecureChatApplicationComponent build();
    }

    void inject (SecureChatApplication app);

}