package com.santiago.securechat.data.repository;

import com.santiago.securechat.data.dao.MessageDao;
import com.santiago.securechat.data.dao.PeerDao;

import java.util.concurrent.Executor;

import javax.inject.Inject;

public class ConversationRepository {

    private final PeerDao peerDao;
    private final MessageDao messageDao;
    private final Executor executor;

    @Inject
    public ConversationRepository (PeerDao peerDao, MessageDao messageDao, Executor executor) {
        this.peerDao = peerDao;
        this.messageDao = messageDao;
        this.executor = executor;
    }
}
