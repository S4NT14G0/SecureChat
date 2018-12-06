/*
  Santiago Roig
  Faten Haji
  Thien Nguyen

  SecureCh@t
 */

package com.santiago.securechat.data.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.santiago.securechat.data.entity.Message;
import com.santiago.securechat.data.entity.Peer;
import com.santiago.securechat.data.repository.ConversationRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * View Model for the Conversation UI
 */
public class ConversationViewModel extends ViewModel {

    private LiveData<List<Message>> peerMessages;

    private final ConversationRepository conversationRepository;

    /**
     * Constructs view model
     * @param conversationRepository - Dependency on conversation repository
     */
    @Inject
    public ConversationViewModel (ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    /**
     * Get all of the messages for a specific peer
     * @param peerId - Peer Id
     * @return - Conversation with peer
     */
    public LiveData<List<Message>> getPeerMessages (final int peerId) {
        peerMessages = conversationRepository.getPeerMessages(peerId);
        return peerMessages;
    }

    /**
     * Send a new conversation request.
     * @param ipAddress - Peer Ip
     * @param port - Peer port
     * @return - New peer
     */
    public Peer requestChat(String ipAddress, int port) {
        return conversationRepository.requestChat(ipAddress, port);
    }

    /**
     * Send an existing peer a message
     * @param peer - Peer
     * @param message - Message
     */
    public void sendPeerMessage (Peer peer, String message) {
        conversationRepository.sendMessage(peer, message);
    }

    /**
     * Find a peer by their Id
     * @param peerId - Peer Id
     * @return - Peer
     */
    public Peer findPeer (int peerId) {
        return conversationRepository.findPeerById(peerId);
    }

}
