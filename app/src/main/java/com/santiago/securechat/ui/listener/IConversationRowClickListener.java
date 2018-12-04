package com.santiago.securechat.ui.listener;

/**
 * Interface that listens for the user selecting a conversation row
 */
public interface IConversationRowClickListener {

    /**
     * Event to fire when conversation is clicked
     * @param peerId - Id of peer who's conversation was clicked
     */
    void onConversationRowClicked (int peerId);
}
