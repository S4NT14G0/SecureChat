package com.santiago.securechat.comm.listener;

public interface IMessageSentListener {
    void onMessageSent(String peerIp, int peerPort, String message, boolean messageSentWithoutException);
}
