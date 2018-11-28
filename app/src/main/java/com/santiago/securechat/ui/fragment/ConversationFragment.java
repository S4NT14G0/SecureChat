package com.santiago.securechat.ui.fragment;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiago.securechat.R;
import com.santiago.securechat.data.entity.Message;
import com.santiago.securechat.data.view_model.ConversationViewModel;
import com.santiago.securechat.ui.MainActivity;
import com.santiago.securechat.ui.adapter.ConversationAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class ConversationFragment extends Fragment {

    @Inject
    public ViewModelProvider.Factory conversationViewModelFactory;

    ConversationViewModel conversationViewModel;

    RecyclerView conversationRecyclerView;
    ConversationAdapter conversationAdapter;
    MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        // Inflate view
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_conversation, container, false);

        // Get reference to parent activity
        mainActivity = (MainActivity) getActivity();

        // Hide Fab button
        mainActivity.setFabVisibility(false);

        // Init observing convsersation
        initConversationViewModel();

        // Init recycle view for messages
        initConversationRecycler(view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
    }

    private void initConversationViewModel() {
        conversationViewModel =  ViewModelProviders.of(this,conversationViewModelFactory).get(ConversationViewModel.class);
        final int peerId = getArguments().getInt("peerId");
        // conversationViewModel.getPeerMessages(peerId).observe(this, messages -> { });
    }

    /**
     * Initialize the conversation history recycler view and set its' data adapter.
     */
    private void initConversationRecycler (View view) {
        // TODO: REMOVE ONCE DATA IS LIVE
        ArrayList<Message> messages = new ArrayList<>();

        Message message = new Message();
        message.setBody("Woooh");
        message.setPeerId(0);
        messages.add(message);

        Message message1 = new Message();
        message1.setBody("Nice convo");
        message1.setPeerId(0);
        messages.add(message1);

        Message message2 = new Message();
        message2.setBody("Thanks!");
        message2.setPeerId(1);
        messages.add(message2);

        Message message3 = new Message();
        message3.setBody("No problemo");
        message3.setPeerId(0);
        messages.add(message3);

        conversationRecyclerView = view.findViewById(R.id.conversation_recycler_view);
        conversationAdapter = new ConversationAdapter(messages);
        conversationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        conversationRecyclerView.setAdapter(conversationAdapter);

    }
}
