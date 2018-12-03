/*
  Santiago Roig
  Faten Haji
  Thien Nguyen

  SecureCh@t
 */

package com.santiago.securechat.comm.listener;

/**
 * Interface which listens for incoming messages
 */
public interface IMessageReceivedListener {
    /**
     * Handle incoming message
     * @param senderIpAddress - Message sender
     * @param message - Message
     */
    void onIncomingMessage (String senderIpAddress, String message);
}
