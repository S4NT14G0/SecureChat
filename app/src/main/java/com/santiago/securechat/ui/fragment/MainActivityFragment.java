package com.santiago.securechat.ui.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiago.securechat.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    RecyclerView conversationHistoryRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initConversationHistoryRecycler(view);
    }

    /**
     * Initialize the conversation history recycler view and set its' data adapter.
     */
    private void initConversationHistoryRecycler (View view) {
        conversationHistoryRecyclerView = view.findViewById(R.id.conversation_list_recycler_view);

    }
}
