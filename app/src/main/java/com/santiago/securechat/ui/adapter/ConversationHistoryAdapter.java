package com.santiago.securechat.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.santiago.securechat.R;
import com.santiago.securechat.data.entity.Peer;
import com.santiago.securechat.ui.listener.IConversationRowClickListener;

import java.util.ArrayList;
import java.util.List;

public class ConversationHistoryAdapter extends RecyclerView.Adapter<ConversationHistoryAdapter.ViewHolder> {

    private List<Peer> conversationHistory;
    private final IConversationRowClickListener iConversationRowClickListener;

    public ConversationHistoryAdapter ( IConversationRowClickListener iConversationRowClickListener) {
        conversationHistory = new ArrayList<>();
        this.iConversationRowClickListener = iConversationRowClickListener;
    }

    @Override
    public ConversationHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_row_item, parent, false);
        return new ViewHolder(v);
    }

    public void setConversationHistory (List<Peer> newData) {
        if (newData == null)
            return;

        this.conversationHistory = newData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ConversationHistoryAdapter.ViewHolder holder, int position) {
        String peerAddress = conversationHistory.get(position).getIpAddress() + ":" + conversationHistory.get(position).getPort();
        holder.tvPeerAddress.setText(peerAddress);

        // TODO: Figure out how to structure conversation so we can get messages from it.
    }

    @Override
    public int getItemCount() {
        return conversationHistory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final TextView tvPeerAddress;
        final TextView tvNewestMessage;

        ViewHolder(View itemView) {
            super(itemView);
            tvPeerAddress = itemView.findViewById(R.id.peer_address_text_view);
            tvNewestMessage = itemView.findViewById(R.id.newest_message_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (iConversationRowClickListener != null) {
                iConversationRowClickListener.onConversationRowClicked(conversationHistory.get(getAdapterPosition()).getId());
            }
        }

    }
}
