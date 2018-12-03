package com.santiago.securechat.comm.listener;

public interface IMessageReceveivedListener {

    void onIncomingMessage (String senderIpAddress, String message);
}
