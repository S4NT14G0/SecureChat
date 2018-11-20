package com.santiago.securechat.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiago.securechat.R;
import com.santiago.securechat.data.Conversation;

import java.util.List;

public class ConversationHistoryAdapter extends RecyclerView.Adapter<ConversationHistoryAdapter.ViewHolder> {

    List<Conversation> conversationHistory;

    public ConversationHistoryAdapter (List<Conversation> conversationHistory) {
        this.conversationHistory = conversationHistory;
    }

    @Override
    public ConversationHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_row_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ConversationHistoryAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
