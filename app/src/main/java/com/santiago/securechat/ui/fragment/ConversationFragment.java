package com.santiago.securechat.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiago.securechat.R;

public class ConversationFragment extends Fragment {

    RecyclerView conversationRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_conversation, container, false);



        return view;
    }

    /**
     * Initialize the conversation history recycler view and set its' data adapter.
     */
    private void initConversationHistoryRecycler (View view) {
        // TODO: REMOVE ONCE DATA IS LIVE
//        conversationHistoryAdapter = new ConversationHistoryAdapter(fauxConversations, this);
//        conversationHistoryRecyclerView = view.findViewById(R.id.conversation_list_recycler_view);
//        conversationHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        conversationHistoryRecyclerView.setAdapter(conversationHistoryAdapter);

    }
}
