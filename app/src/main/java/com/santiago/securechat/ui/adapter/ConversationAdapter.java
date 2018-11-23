package com.santiago.securechat.ui.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.santiago.securechat.R;
import com.santiago.securechat.data.entity.Message;

import java.util.List;

import static android.view.Gravity.RIGHT;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ViewHolder> {

    List<Message> messageList;

    public ConversationAdapter (List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String message = messageList.get(position).getBody();
        holder.tvMessage.setText(message);

        //TODO: Replace with real logic
        if (messageList.get(position).getId() == 0) {
            holder.cardMessage.setForegroundGravity(RIGHT);

        } else {

        }

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

        TextView tvMessage;
        CardView cardMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.message_text_view);
            cardMessage = itemView.findViewById(R.id.message_card);
        }
    }
}
