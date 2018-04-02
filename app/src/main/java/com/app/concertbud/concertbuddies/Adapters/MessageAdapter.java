package com.app.concertbud.concertbuddies.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.concertbud.concertbuddies.Networking.Responses.Message;
import com.app.concertbud.concertbuddies.R;
//import com.app.concertbud.concertbuddies.ViewHolders.MessageViewHolder;
import com.app.concertbud.concertbuddies.ViewHolders.ReceivedMessageHolder;
import com.app.concertbud.concertbuddies.ViewHolders.SentMessageHolder;
import com.facebook.Profile;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.ObservableSnapshotArray;

/**
 * Created by huongnguyen on 3/26/18.
 */

public class MessageAdapter extends FirebaseRecyclerAdapter<Message, RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_MSG_SENT = 1;
    private static final int VIEW_TYPE_MSG_RECEIVED = 2;
    private ObservableSnapshotArray<Message> messages;
    public MessageAdapter(@NonNull FirebaseRecyclerOptions<Message> options) {
        super(options);
        messages = options.getSnapshots();
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull Message model) {
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MSG_SENT:
                ((SentMessageHolder)holder).bind(model);
                break;
            case VIEW_TYPE_MSG_RECEIVED:
                ((ReceivedMessageHolder)holder).bind(model);
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_MSG_SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_msg_sent, parent, false);
            return new SentMessageHolder(view);
        }
        else if (viewType == VIEW_TYPE_MSG_RECEIVED) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_msg_received, parent, false);
            return new ReceivedMessageHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        //Message msg = getItem(position);
        Message msg = messages.get(position);
        if (msg.getSenderName() != null && msg.getSenderName().equals(Profile.getCurrentProfile().getName())) {
            return VIEW_TYPE_MSG_SENT;
        }
        else {
            return VIEW_TYPE_MSG_RECEIVED;
        }
    }

}
