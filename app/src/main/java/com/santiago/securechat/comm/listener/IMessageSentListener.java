/*
  Santiago Roig
  Faten Haji
  Thien Nguyen

  SecureCh@t
 */

package com.santiago.securechat.comm.listener;

/**
 * Interface which listens for outgoing messages
 */
public interface IMessageSentListener {

    /**
     * Handle outgoing message
     * @param peerIp - Peer's IP Address
     * @param peerPort - Peer's Port
     * @param message - Message
     * @param messageSentWithoutException - Message send status
     */
    void onMessageSent(String peerIp, int peerPort, String message, boolean messageSentWithoutException);
}
