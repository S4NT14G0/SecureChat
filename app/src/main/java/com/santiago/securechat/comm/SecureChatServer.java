package com.santiago.securechat.comm;

import android.app.Application;

import com.santiago.securechat.comm.listener.IMessageReceveivedListener;

import java.util.concurrent.Executor;

import javax.inject.Inject;

public class SecureChatServer {

    private final Executor executor;
    private final Application application;

    private IMessageReceveivedListener iMessageReceveivedListener;

    @Inject
    public SecureChatServer (Executor executor, Application application) {
        this.executor = executor;
        this.application = application;
    }

    public void setMessageReceveivedListener (IMessageReceveivedListener iMessageReceveivedListener) {
        this.iMessageReceveivedListener = iMessageReceveivedListener;
    }

}
