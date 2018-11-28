package com.santiago.securechat.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.santiago.securechat.R;
import com.santiago.securechat.ui.fragment.ConversationHistoryFragment;
import com.santiago.securechat.ui.listener.IFabClickListener;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    FloatingActionButton fab;
    IFabClickListener iFabClickListener;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AndroidInjection.inject(this);
        fab =  findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            if (iFabClickListener != null) {
                iFabClickListener.onFabClick();
            }
        });

        ConversationHistoryFragment conversationHistoryFragment = new ConversationHistoryFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, conversationHistoryFragment);
        fragmentTransaction.commit();
    }

    public void setIFabClickListener(IFabClickListener iFabClickListener) {
        this.iFabClickListener = iFabClickListener;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    public void unregisterIFabClickListener() {
        this.iFabClickListener = null;
    }

    public void setFabVisibility (boolean isVisible) {
        if (fab != null) {
            fab.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }

}
