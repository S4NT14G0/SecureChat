/*
  Santiago Roig
  Faten Haji
  Thien Nguyen

  SecureCh@t
 */


package com.santiago.securechat.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.santiago.securechat.R;
import com.santiago.securechat.data.entity.Message;

import java.util.ArrayList;
import java.util.List;

import static android.view.Gravity.END;
import static android.view.Gravity.START;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ViewHolder> {

    private List<Message> messageList;

    public ConversationAdapter () {
        this.messageList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message messageItem = messageList.get(position);
        String message = messageItem.getSecureMessage().getMessage();
        holder.tvMessage.setText(message);

        if (messageItem.isOutgoingMessage()) {
            holder.viewMessage.setGravity(START);

            if (!messageItem.isSendSuccessful()) {
                holder.cardView.setCardBackgroundColor(Color.RED);
            }
        } else {
            holder.viewMessage.setGravity(END);
        }
    }

    public void setMessages (List<Message> newData) {
        this.messageList = newData;
        notifyDataSetChanged();
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {

        final TextView tvMessage;
        final LinearLayout viewMessage;
        final CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.message_text_view);
            viewMessage = itemView.findViewById(R.id.message_view);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
