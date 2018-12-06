/*
  Santiago Roig
  Faten Haji
  Thien Nguyen

  SecureCh@t
 */

package com.santiago.securechat.data;

import com.santiago.securechat.data.entity.Peer;

/**
 * Listens for new peer requests.
 */
public interface INewPeerRequestListener {
    /**
     * New peer callback.
     * @param peer - Peer who wants to chat.
     */
    void onNewPeerRequest (Peer peer);
}
