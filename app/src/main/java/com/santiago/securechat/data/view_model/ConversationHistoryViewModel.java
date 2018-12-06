/*
  Santiago Roig
  Faten Haji
  Thien Nguyen

  SecureCh@t
 */

package com.santiago.securechat.data.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.santiago.securechat.data.INewPeerRequestListener;
import com.santiago.securechat.data.entity.Peer;
import com.santiago.securechat.data.repository.ConversationRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * View Model for the Conversation History UI
 */
public class ConversationHistoryViewModel extends ViewModel {

    private LiveData<List<Peer>> peers;

    private final ConversationRepository conversationRepository;

    /**
     * Constructor
     * @param conversationRepository - Conversation Repo Dependency
     */
    @Inject
    public ConversationHistoryViewModel(final ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;

        peers = conversationRepository.getPeers();
    }

    /**
     * Get observable list of peers
     * @return List of peers
     */
    public LiveData<List<Peer>> getPeers() {
        return peers;
    }

    /**
     * Registers peer request listener.
     * @param iNewPeerRequestListener - Listens for new peer requests
     */
    public void registerNewPeerRequestListener(INewPeerRequestListener iNewPeerRequestListener) {
        conversationRepository.registerNewPeerRequestListener(iNewPeerRequestListener);
    }

    /**
     * Unregister peer listener to avoid memory leak
     */
    public void unregisterNewPeerRequestListener () {
        conversationRepository.unregisterNewPeerRequestListener();
    }

    /**
     * Pass peer blacklist status down to repository
     * @param peer - Peer
     * @param isBlackListed - Blacklisted?
     */
    public void setPeerBlackListed (Peer peer, boolean isBlackListed) {
        conversationRepository.setPeerBlackListed(peer, isBlackListed);
    }
}
