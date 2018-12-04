package com.santiago.securechat.data.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.santiago.securechat.data.entity.Message;
import com.santiago.securechat.data.entity.Peer;
import com.santiago.securechat.data.repository.ConversationRepository;

import java.util.List;

import javax.inject.Inject;

public class ConversationViewModel extends ViewModel {

    private LiveData<List<Message>> peerMessages;

    private final ConversationRepository conversationRepository;

    @Inject
    public ConversationViewModel (ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public LiveData<List<Message>> getPeerMessages (final int peerId) {
        peerMessages = conversationRepository.getPeerMessages(peerId);
        return peerMessages;
    }

    public Peer createNewPeer (String ipAddress, int port) {
        return conversationRepository.requestChat(ipAddress, port);
    }

    public void sendPeerMessage (Peer peer, String message) {
        conversationRepository.sendMessage(peer, message);
    }

    public Peer findPeer (int peerId) {
        return conversationRepository.findPeerById(peerId);
    }

}
