package com.santiago.securechat.ui.listener;

/**
 * Interface that listens for the user selecting a conversation row
 */
public interface IConversationRowClickListener {

    /**
     * Event to fire when conversation is clicked
     * @param conversationId - Id of conversation clicked
     */
    void onConversationRowClicked (int conversationId);
}
