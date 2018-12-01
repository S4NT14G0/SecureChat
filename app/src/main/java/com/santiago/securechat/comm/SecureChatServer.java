package com.santiago.securechat.comm;

import com.santiago.securechat.SecureChatApplication;
import com.santiago.securechat.comm.listener.IMessageReceveivedListener;

import java.util.concurrent.Executor;

import javax.inject.Inject;

public class SecureChatServer {

    private final Executor executor;
    private IMessageReceveivedListener iMessageReceveivedListener;

    @Inject
    public SecureChatServer (Executor executor) {
        this.executor = executor;
    }

    public void setMessageReceveivedListener (IMessageReceveivedListener iMessageReceveivedListener) {
        this.iMessageReceveivedListener = iMessageReceveivedListener;
    }

}
