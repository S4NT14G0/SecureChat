package com.santiago.securechat.comm;

import com.santiago.securechat.SecureChatApplication;
import com.santiago.securechat.comm.listener.IMessageReceveivedListener;

import java.util.concurrent.Executor;

import javax.inject.Inject;

public class SecureChatServer {

    private final Executor executor;
    private final SecureChatApplication secureChatApplication;
    private IMessageReceveivedListener iMessageReceveivedListener;

    @Inject
    public SecureChatServer (Executor executor, SecureChatApplication secureChatApplication) {
        this.executor = executor;
        this.secureChatApplication = secureChatApplication;

    }

    public void setMessageReceveivedListener (IMessageReceveivedListener iMessageReceveivedListener) {
        this.iMessageReceveivedListener = iMessageReceveivedListener;
    }
}
