package com.santiago.securechat.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.santiago.securechat.R;
import com.santiago.securechat.data.INewPeerRequestListener;
import com.santiago.securechat.data.entity.Peer;
import com.santiago.securechat.data.view_model.ConversationHistoryViewModel;
import com.santiago.securechat.ui.fragment.ConversationHistoryFragment;
import com.santiago.securechat.ui.listener.IFabClickListener;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector, INewPeerRequestListener {

    FloatingActionButton fab;
    IFabClickListener iFabClickListener;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    public ViewModelProvider.Factory conversationHistoryViewModelFactory;
    ConversationHistoryViewModel conversationHistoryViewModel;

    @Override
    protected void onDestroy() {
        conversationHistoryViewModel.unregisterNewPeerRequestListener();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AndroidInjection.inject(this);
        initConversationHistoryViewModel();


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

    private void initConversationHistoryViewModel() {
        conversationHistoryViewModel =  ViewModelProviders.of(this,conversationHistoryViewModelFactory).get(ConversationHistoryViewModel.class);
        conversationHistoryViewModel.registerNewPeerRequestListener(this);
    }

    public void unregisterIFabClickListener() {
        this.iFabClickListener = null;
    }

    public void setFabVisibility (boolean isVisible) {
        if (fab != null) {
            fab.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onNewPeerRequest(Peer peer) {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    conversationHistoryViewModel.setPeerBlackListed(peer, false);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //Blacklist button click
                    conversationHistoryViewModel.setPeerBlackListed(peer, true);

                    break;
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String newPeerDialogMessage = "Message from " + peer.getIpAddress() + ":" + peer.getPort();

        builder.setMessage(newPeerDialogMessage)
                .setPositiveButton("Accept", dialogClickListener)
                .setNegativeButton("Black List Peer", dialogClickListener).show();
    }
}
