package com.santiago.securechat.data.dao.type_converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.santiago.securechat.data.entity.Peer;

public class PeerConverter {

    @TypeConverter
    public static Peer fromString (String value) {
        return new Gson().fromJson(value, Peer.class);
    }

    @TypeConverter
    public static String fromPeer (Peer peer) {
        Gson gson = new Gson();
        String peerJson = gson.toJson(peer);
        return peerJson;
    }
}
