package com.santiago.securechat.data.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

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
}
