package com.santiago.securechat.data;

import com.santiago.securechat.data.entity.Peer;

public interface INewPeerRequestListener {
    void onNewPeerRequest (Peer peer);
}
