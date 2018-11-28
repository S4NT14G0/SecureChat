package com.santiago.securechat.data.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.santiago.securechat.data.entity.Message;
import com.santiago.securechat.data.repository.ConversationRepository;

import java.util.List;

import javax.inject.Inject;

public class ConversationViewModel extends ViewModel {

    private LiveData<Message> peerMessages;

    private final ConversationRepository conversationRepository;

    @Inject
    public ConversationViewModel (ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public LiveData<List<Message>> getPeerMessages (final int peerId) {
        // TODO: peerMessages = conversationRepository.getPeerMessages(peerId);
        return null;
    }


}
