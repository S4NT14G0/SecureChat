/*
  Santiago Roig
  Faten Haji
  Thien Nguyen

  SecureCh@t
 */

package com.santiago.securechat.ui.fragment;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.santiago.securechat.R;
import com.santiago.securechat.data.entity.Peer;
import com.santiago.securechat.data.view_model.ConversationHistoryViewModel;
import com.santiago.securechat.data.view_model.ConversationViewModel;
import com.santiago.securechat.ui.MainActivity;
import com.santiago.securechat.ui.adapter.ConversationHistoryAdapter;
import com.santiago.securechat.ui.listener.IConversationRowClickListener;
import com.santiago.securechat.ui.listener.IFabClickListener;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

import static com.santiago.securechat.data.entity.Peer.NEW_PEER_ARGUMENT_VALUE;
import static com.santiago.securechat.data.entity.Peer.PEER_ID_ARGUMENTS_KEY;

/**
 * A placeholder fragment containing a the conversation history
 */
public class ConversationHistoryFragment extends Fragment implements IFabClickListener, IConversationRowClickListener {

    @Inject
    public ViewModelProvider.Factory conversationHistoryViewModelFactory;

    MainActivity mainActivity;
    ConversationHistoryViewModel conversationHistoryViewModel;
    RecyclerView conversationHistoryRecyclerView;
    ConversationHistoryAdapter conversationHistoryAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_conversation_history, container, false);

        initConversationHistoryRecycler(view);

        initConversationHistoryViewModel();

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
    }

    private void initConversationHistoryViewModel() {
        conversationHistoryViewModel =  ViewModelProviders.of(this,conversationHistoryViewModelFactory).get(ConversationHistoryViewModel.class);
        conversationHistoryViewModel.getPeers().observe(this, peers -> conversationHistoryAdapter.setConversationHistory(peers));
    }

    /**
     * Initialize the conversation history recycler view and set its' data adapter.
     */
    private void initConversationHistoryRecycler (View view) {
        conversationHistoryAdapter = new ConversationHistoryAdapter(this);
        conversationHistoryRecyclerView = view.findViewById(R.id.conversation_list_recycler_view);
        conversationHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        conversationHistoryRecyclerView.setAdapter(conversationHistoryAdapter);
    }

    /**
     * Event to fire when conversation is clicked
     *
     * @param peerId - Id of conversation clicked
     */
    @Override
    public void onConversationRowClicked(int peerId) {
        openConversation(peerId);
    }

    /**
     * Event to fire when fab is clicked
     */
    @Override
    public void onFabClick() {
        openConversation(NEW_PEER_ARGUMENT_VALUE);
    }

    void openConversation (int peerId) {
        ConversationFragment conversationFragment = new ConversationFragment();
        Bundle args = new Bundle();
        args.putInt(PEER_ID_ARGUMENTS_KEY, peerId);
        conversationFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, conversationFragment, "conversation");
        transaction.addToBackStack("");
        transaction.commit();
    }
}
