/*
  Santiago Roig
  Faten Haji
  Thien Nguyen

  SecureCh@t
 */

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

/**
 * ConversationRepository is the controller for the application.
 * It's responsibilities include interfacing with the
 * DAOs, SecureChatClient and SecureChatServer.
 */
public class ConversationRepository implements IMessageSentListener, IMessageReceivedListener {

    private final PeerDao peerDao;
    private final MessageDao messageDao;
    private final Executor executor;
    private final SecureChatClient secureChatClient;

    private INewPeerRequestListener iNewPeerRequestListener;


    /**
     * See class description above.
     * @param peerDao - Peer database DAO
     * @param messageDao - Message database DAO
     * @param secureChatServer - Receives messages from peers
     * @param secureChatClient - Sends messages to peers
     * @param executor - Thread management
     */
    @Inject
    public ConversationRepository (PeerDao peerDao, MessageDao messageDao, SecureChatServer secureChatServer, SecureChatClient secureChatClient, Executor executor) {
        this.peerDao = peerDao;
        this.messageDao = messageDao;
        this.secureChatClient = secureChatClient;
        this.executor = executor;

        secureChatServer.setMessageReceivedListener(this);
        secureChatServer.run();
    }

    /**
     * Get all of this device's peers.
     * @see LiveData for information about
     * observer pattern.
     * @return Peers
     */
    public LiveData<List<Peer>> getPeers () {
        return peerDao.getPeers();
    }

    /**
     * Send a chat request to a peer we haven't spoken to before
     * @param ipAddress - Peer IP Address
     * @param port - Peer port
     * @return Return the newly created peer
     */
    public Peer requestChat (String ipAddress, int port) {
        int peerId = createPeer(ipAddress, port);

        secureChatClient.sendMessage("Howdy", ipAddress, port, null);

        return peerDao.findPeerById(peerId);
    }

    /**
     * Send message to a peer.
     * @param peer - Peer
     * @param message - Message
     */
    public void sendMessage (Peer peer, String message) {
        secureChatClient.sendMessage(message, peer.getIpAddress(), peer.getPort(), this);
    }

    /**
     * Find all of the messages from a specific peer.
     * @param peerId - Peer to get messages from
     * @return Peer messages
     */
    public LiveData<List<Message>> getPeerMessages (int peerId) {
        return messageDao.findMessagesForPeer(peerId);
    }

    /**
     * Find a peer by their Id
     * @param peerId - Peer Id
     * @return Peer
     */
    public Peer findPeerById(int peerId) {
        return peerDao.findPeerById(peerId);
    }

    /**
     *
     * @param peerIp - Peer's IP Address
     * @param peerPort - Peer's Port
     * @param message - Message
     * @param messageSentWithoutException - Message send status
     */
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

    /**
     * Handle an incoming message
     * @param senderIpAddress - Message sender
     * @param message - Message
     */
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

    /**
     * Create a new peer
     * @param ipAddress - Peer IP
     * @param port - Peer Port
     * @return - New peer's Id
     */
    int  createPeer (String ipAddress, int port) {
        Peer peer = new Peer(ipAddress, port);
        return (int) peerDao.insert(peer);
    }

    /**
     * Unregister new peer request listener to avoid memory leaks.
     */
    public void unregisterNewPeerRequestListener () {
        this.iNewPeerRequestListener = null;
    }

    /**
     * Callback for an incoming peer request.
     * @param iNewPeerRequestListener - Listens for peer requests.
     */
    public void registerNewPeerRequestListener(INewPeerRequestListener iNewPeerRequestListener) {
        this.iNewPeerRequestListener = iNewPeerRequestListener;
    }

    /**
     * Set peer's black listed status
     * @param peer - Peer
     * @param isBlackListed - Did app user black list peer?
     */
    public void setPeerBlackListed (Peer peer, boolean isBlackListed) {
        peer.setBlackListed(isBlackListed);
        peerDao.update(peer);
    }
}
