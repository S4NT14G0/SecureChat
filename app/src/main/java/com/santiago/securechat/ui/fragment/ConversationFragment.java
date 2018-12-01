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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.santiago.securechat.R;
import com.santiago.securechat.data.entity.Peer;
import com.santiago.securechat.data.view_model.ConversationViewModel;
import com.santiago.securechat.ui.MainActivity;
import com.santiago.securechat.ui.adapter.ConversationAdapter;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

import static android.view.View.GONE;
import static com.santiago.securechat.data.entity.Peer.NEW_PEER_ARGUMENT_VALUE;
import static com.santiago.securechat.data.entity.Peer.PEER_ID_ARGUMENTS_KEY;

public class ConversationFragment extends Fragment {

    @Inject
    public ViewModelProvider.Factory conversationViewModelFactory;

    ConversationViewModel conversationViewModel;

    RecyclerView conversationRecyclerView;
    ConversationAdapter conversationAdapter;
    MainActivity mainActivity;

    View viewNewConversationWidgetGroup, viewMessageSendingWidgetGroup;
    EditText etNewPeerIp, etNewPeerPort, etMessage;
    Button btnSend, btnStartConversation;
    TextView tvPeerName;

    Peer peer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        // Inflate view
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_conversation, container, false);

        // Get view model
        conversationViewModel =  ViewModelProviders.of(this,conversationViewModelFactory).get(ConversationViewModel.class);

        // Get reference to parent activity
        mainActivity = (MainActivity) getActivity();

        // Hide Fab button
        mainActivity.setFabVisibility(false);

        // Init recycle view for messages
        initConversationRecycler(view);

        initUIWidgets (view);

        final int peerId = getArguments().getInt(PEER_ID_ARGUMENTS_KEY);

        // Check if conversation exists
        if (peerId == NEW_PEER_ARGUMENT_VALUE) {
            displayNewChatScreen();
        } else {
            // Hide new conversation widgets
            viewNewConversationWidgetGroup.setVisibility(View.GONE);
            // Find peer
            peer = conversationViewModel.findPeer(peerId);
            // Set convo label
            tvPeerName.setText(peer.getIpAddress() + ":" + peer.getPort());
            // Init observing conversation
            observerPeerMessages(peer.getId());
        }

        return view;
    }

    private void initUIWidgets(View view) {
        viewNewConversationWidgetGroup = view.findViewById(R.id.view_new_covnersation);
        etNewPeerIp = view.findViewById(R.id.et_new_peer_ip);
        etNewPeerPort = view.findViewById(R.id.et_new_peer_port);
        btnStartConversation = view.findViewById(R.id.btn_new_conversation_start);

        viewMessageSendingWidgetGroup = view.findViewById(R.id.view_message_send);
        etMessage = view.findViewById(R.id.et_message);
        btnSend = view.findViewById(R.id.btn_send);
        btnSend.setOnClickListener(button -> onSendButtonClicked());

        tvPeerName = view.findViewById(R.id.tv_peer_name);

    }

    private void onSendButtonClicked () {
        if (!etMessage.getText().toString().isEmpty()) {

            String message = etMessage.getText().toString();

            if (!message.isEmpty())
                conversationViewModel.sendPeerMessage(peer, message);

            etMessage.setText("");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
    }

    /**
     * Initialize the conversation history recycler view and set its' data adapter.
     */
    private void initConversationRecycler (View view) {
        conversationRecyclerView = view.findViewById(R.id.conversation_recycler_view);
        conversationAdapter = new ConversationAdapter();
        conversationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        conversationRecyclerView.setAdapter(conversationAdapter);

    }

    private void displayNewChatScreen() {
        // Hide Peer TextView
        tvPeerName.setVisibility(GONE);
        viewNewConversationWidgetGroup.setVisibility(View.VISIBLE);
        viewMessageSendingWidgetGroup.setVisibility(View.GONE);

        btnStartConversation.setOnClickListener(view -> {

            String peerIp = etNewPeerIp.getText().toString();
            int peerPort = Integer.valueOf(etNewPeerPort.getText().toString());

            peer = conversationViewModel.createNewPeer(peerIp, peerPort);

            // Show the messaging widgets
            tvPeerName.setVisibility(View.VISIBLE);
            viewNewConversationWidgetGroup.setVisibility(View.GONE);
            viewMessageSendingWidgetGroup.setVisibility(View.VISIBLE);

            tvPeerName.setText(peerIp + ":" + peerPort);
            observerPeerMessages(peer.getId());
        });
    }

    private void observerPeerMessages(int peerId) {
        conversationViewModel.getPeerMessages(peerId).observe(this, messages -> conversationAdapter.setMessages(messages));
    }
}
