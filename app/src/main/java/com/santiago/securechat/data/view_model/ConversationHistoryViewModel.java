package com.santiago.securechat.data.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.santiago.securechat.data.INewPeerRequestListener;
import com.santiago.securechat.data.entity.Peer;
import com.santiago.securechat.data.repository.ConversationRepository;

import java.util.List;

import javax.inject.Inject;

public class ConversationHistoryViewModel extends ViewModel {

    private LiveData<List<Peer>> peers;

    private final ConversationRepository conversationRepository;

    @Inject
    public ConversationHistoryViewModel(final ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;

        peers = conversationRepository.getPeers();
    }

    public LiveData<List<Peer>> getPeers() {
        return peers;
    }

    public void registerNewPeerRequestListener(INewPeerRequestListener iNewPeerRequestListener) {
        conversationRepository.registerNewPeerRequestListener(iNewPeerRequestListener);
    }

    public void unregisterNewPeerRequestListener () {
        conversationRepository.unregisterNewPeerRequestListener();
    }

    public void setPeerBlackListed (Peer peer, boolean isBlackListed) {
        conversationRepository.setPeerBlackListed(peer, isBlackListed);
    }
}
