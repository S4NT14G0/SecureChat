package com.santiago.securechat.di.module;

import com.santiago.securechat.ui.MainActivity;
import com.santiago.securechat.ui.fragment.ConversationFragment;
import com.santiago.securechat.ui.fragment.ConversationHistoryFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AndroidViewInjectorModule {

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract ConversationFragment bindConversationFragment();

    @ContributesAndroidInjector
    abstract ConversationHistoryFragment bindConversationHistoryFragment();

}
