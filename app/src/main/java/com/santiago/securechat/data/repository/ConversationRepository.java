package com.santiago.securechat.data.repository;

import android.arch.lifecycle.LiveData;

import com.santiago.securechat.comm.SecureChatServer;
import com.santiago.securechat.data.dao.MessageDao;
import com.santiago.securechat.data.dao.PeerDao;
import com.santiago.securechat.data.entity.Message;
import com.santiago.securechat.data.entity.Peer;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

public class ConversationRepository {

    private final PeerDao peerDao;
    private final MessageDao messageDao;
    private final Executor executor;
    private final SecureChatServer secureChatServer;

    @Inject
    public ConversationRepository (PeerDao peerDao, MessageDao messageDao, SecureChatServer secureChatServer, Executor executor) {
        this.peerDao = peerDao;
        this.messageDao = messageDao;
        this.secureChatServer = secureChatServer;
        this.executor = executor;
    }

    public LiveData<List<Peer>> getPeers () {
        return peerDao.getPeers();
    }

    public long requestChat (String ipAddress, int port) {
        Peer peer = new Peer(ipAddress, port);
        long peerId = peerDao.insert(peer);
        return peerId;
    }

    public LiveData<List<Message>> getPeerMessages (int peerId) {
        return messageDao.findMessagesForPeer(peerId);
    }
}
