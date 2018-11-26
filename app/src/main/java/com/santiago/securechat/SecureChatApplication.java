package com.santiago.securechat;

import android.app.Activity;
import android.app.Application;

import com.santiago.securechat.di.component.DaggerSecureChatApplicationComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class SecureChatApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        // Configure our application to use Dagger Dependency Injection
        DaggerSecureChatApplicationComponent
                .builder()
                .application(this)
                .build()
                .inject(this);
    }

    /**
     * Returns an {@link AndroidInjector} of {@link Activity}s.
     */
    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
