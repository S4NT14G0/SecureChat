package com.santiago.securechat.data.repository;

import android.arch.lifecycle.LiveData;

import com.santiago.securechat.comm.SecureChatClient;
import com.santiago.securechat.comm.SecureChatServer;
import com.santiago.securechat.comm.listener.IMessageSentListener;
import com.santiago.securechat.data.dao.MessageDao;
import com.santiago.securechat.data.dao.PeerDao;
import com.santiago.securechat.data.entity.Message;
import com.santiago.securechat.data.entity.Peer;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

public class ConversationRepository implements IMessageSentListener {

    private final PeerDao peerDao;
    private final MessageDao messageDao;
    private final Executor executor;
    private final SecureChatServer secureChatServer;
    private final SecureChatClient secureChatClient;


    @Inject
    public ConversationRepository (PeerDao peerDao, MessageDao messageDao, SecureChatServer secureChatServer, SecureChatClient secureChatClient, Executor executor) {
        this.peerDao = peerDao;
        this.messageDao = messageDao;
        this.secureChatServer = secureChatServer;
        this.secureChatClient = secureChatClient;
        this.executor = executor;
    }

    public LiveData<List<Peer>> getPeers () {
        return peerDao.getPeers();
    }

    public Peer requestChat (String ipAddress, int port) {
        Peer peer = new Peer(ipAddress, port);
        int peerId = (int) peerDao.insert(peer);

        // TODO: Send a well formed hello message
        secureChatClient.sendMessage("HI", ipAddress, port, null);

        return peerDao.findPeerById(peerId);
    }

    public void sendMessage (Peer peer, String message) {
        secureChatClient.sendMessage(message, peer.getIpAddress(), peer.getPort(), this);
    }

    public LiveData<List<Message>> getPeerMessages (int peerId) {
        return messageDao.findMessagesForPeer(peerId);
    }


    public Peer findPeerById(int peerId) {
        return peerDao.findPeerById(peerId);
    }

    @Override
    public void onMessageSent(String peerIp, int peerPort, String message, boolean messageSentWithoutException) {

        executor.execute(() -> {
            Peer peer = peerDao.findPeerByNetworkId(peerIp, peerPort);

            Message messageItem = new Message();
            messageItem.setPeerId(peer.getId());
            messageItem.setBody(message);
            messageItem.setSendSuccessful(messageSentWithoutException);

            messageDao.insert(messageItem);

        });
    }
}
