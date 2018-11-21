package com.santiago.securechat.ui;

import android.opengl.Visibility;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.santiago.securechat.R;
import com.santiago.securechat.ui.listener.IFabClickListener;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    IFabClickListener iFabClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab =  findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            if (iFabClickListener != null) {
                iFabClickListener.onFabClick();
            }
        });
    }

    public void setIFabClickListener(IFabClickListener iFabClickListener) {
        this.iFabClickListener = iFabClickListener;
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
