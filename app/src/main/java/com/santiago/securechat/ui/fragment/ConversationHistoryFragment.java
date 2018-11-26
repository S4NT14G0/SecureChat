package com.santiago.securechat.ui.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.santiago.securechat.R;
import com.santiago.securechat.data.entity.Peer;
import com.santiago.securechat.ui.MainActivity;
import com.santiago.securechat.ui.adapter.ConversationHistoryAdapter;
import com.santiago.securechat.ui.listener.IConversationRowClickListener;
import com.santiago.securechat.ui.listener.IFabClickListener;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a the conversation history
 */
public class ConversationHistoryFragment extends Fragment implements IFabClickListener, IConversationRowClickListener {

    MainActivity mainActivity;

    RecyclerView conversationHistoryRecyclerView;
    ConversationHistoryAdapter conversationHistoryAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_conversation_history, container, false);
        initConversationHistoryRecycler(view);
        mainActivity = (MainActivity) getActivity();
        mainActivity.setFabVisibility(true);
        mainActivity.setIFabClickListener(this);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        mainActivity.unregisterIFabClickListener();
    }

    /**
     * Initialize the conversation history recycler view and set its' data adapter.
     */
    private void initConversationHistoryRecycler (View view) {
        // TODO: REMOVE ONCE DATA IS LIVE
        ArrayList<Peer> fauxConversations = new ArrayList<>();
        fauxConversations.add(new Peer("192.168.10.235", 5050));
        fauxConversations.add(new Peer("192.168.10.11", 2222));


        conversationHistoryAdapter = new ConversationHistoryAdapter(fauxConversations, this);
        conversationHistoryRecyclerView = view.findViewById(R.id.conversation_list_recycler_view);
        conversationHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        conversationHistoryRecyclerView.setAdapter(conversationHistoryAdapter);

    }

    /**
     * Event to fire when conversation is clicked
     *
     * @param conversationId - Id of conversation clicked
     */
    @Override
    public void onConversationRowClicked(int conversationId) {
        ConversationFragment conversationFragment = new ConversationFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, conversationFragment, "conversation");
        transaction.addToBackStack("");
        transaction.commit();
    }

    /**
     * Event to fire when fab is clicked
     */
    @Override
    public void onFabClick() {
        Toast.makeText(getActivity(), "New Conversation", Toast.LENGTH_LONG).show();
    }
}
