package com.santiago.securechat.data.repository;

import android.arch.lifecycle.LiveData;

import com.santiago.securechat.comm.SecureChatClient;
import com.santiago.securechat.comm.SecureChatServer;
import com.santiago.securechat.comm.listener.IMessageReceivedListener;
import com.santiago.securechat.comm.listener.IMessageSentListener;
import com.santiago.securechat.data.INewPeerRequestListener;
import com.santiago.securechat.data.dao.MessageDao;
import com.santiago.securechat.data.dao.PeerDao;
import com.santiago.securechat.data.entity.Message;
import com.santiago.securechat.data.entity.Peer;
import com.santiago.securechat.data.entity.SecureMessage;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

public class ConversationRepository implements IMessageSentListener, IMessageReceivedListener {

    private final PeerDao peerDao;
    private final MessageDao messageDao;
    private final Executor executor;
    private final SecureChatClient secureChatClient;

    private INewPeerRequestListener iNewPeerRequestListener;


    @Inject
    public ConversationRepository (PeerDao peerDao, MessageDao messageDao, SecureChatServer secureChatServer, SecureChatClient secureChatClient, Executor executor) {
        this.peerDao = peerDao;
        this.messageDao = messageDao;
        this.secureChatClient = secureChatClient;
        this.executor = executor;

        secureChatServer.setMessageReceivedListener(this);
        secureChatServer.run();
    }

    public LiveData<List<Peer>> getPeers () {
        return peerDao.getPeers();
    }

    public Peer requestChat (String ipAddress, int port) {
        int peerId = createPeer(ipAddress, port);

        secureChatClient.sendMessage("Howdy", ipAddress, port, null);

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
            Peer peer = peerDao.findPeerByNetworkId(peerIp);

            Message messageItem = new Message();
            messageItem.setPeerId(peer.getId());
            messageItem.setSecureMessage(new SecureMessage(message));
            messageItem.setOutgoingMessage(true);
            messageItem.setSendSuccessful(messageSentWithoutException);

            messageDao.insert(messageItem);

        });
    }

    @Override
    public void onIncomingMessage(String senderIpAddress, String message) {

        executor.execute(() -> {

            Peer peer = peerDao.findPeerByNetworkId(senderIpAddress);

            if (peer != null) {
                if (peer.isBlackListed())
                    return;

                Message messageItem = new Message();
                messageItem.setPeerId(peer.getId());
                messageItem.setSecureMessage(new SecureMessage(message));
                messageItem.setOutgoingMessage(false);
                messageDao.insert(messageItem);
            } else {
                int peerId = createPeer(senderIpAddress, 9999);

                Message messageItem = new Message();
                messageItem.setPeerId(peerId);
                messageItem.setSecureMessage(new SecureMessage(message));
                messageItem.setOutgoingMessage(false);
                if (iNewPeerRequestListener != null)
                    iNewPeerRequestListener.onNewPeerRequest(peerDao.findPeerById(peerId));
            }
        });
    }

    int  createPeer (String ipAddress, int port) {
        Peer peer = new Peer(ipAddress, port);
        return (int) peerDao.insert(peer);
    }

    public void unregisterNewPeerRequestListener () {
        this.iNewPeerRequestListener = null;
    }

    public void registerNewPeerRequestListener(INewPeerRequestListener iNewPeerRequestListener) {
        this.iNewPeerRequestListener = iNewPeerRequestListener;
    }

    public void setPeerBlackListed (Peer peer, boolean isBlackListed) {
        peer.setBlackListed(isBlackListed);
        peerDao.update(peer);
    }
}
