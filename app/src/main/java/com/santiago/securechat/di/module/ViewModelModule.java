/*
  Santiago Roig
  Faten Haji
  Thien Nguyen

  SecureCh@t
 */


package com.santiago.securechat.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.santiago.securechat.data.view_model.ConversationHistoryViewModel;
import com.santiago.securechat.data.view_model.ConversationViewModel;
import com.santiago.securechat.data.view_model.FactoryViewModel;
import com.santiago.securechat.di.key.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ConversationHistoryViewModel.class)
    abstract ViewModel bindConversationHistoryFragmentViewModel (ConversationHistoryViewModel conversationHistoryViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ConversationViewModel.class)
    abstract ViewModel bindConversationFragmentViewModel (ConversationViewModel conversationViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory (FactoryViewModel factory);
}
