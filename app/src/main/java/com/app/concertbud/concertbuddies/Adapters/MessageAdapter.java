package com.app.concertbud.concertbuddies.Adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.concertbud.concertbuddies.Networking.Responses.Message;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.ViewHolders.MessageViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

/**
 * Created by huongnguyen on 3/26/18.
 */

public class MessageAdapter extends FirebaseRecyclerAdapter<Message, MessageViewHolder> {

    public MessageAdapter(@NonNull FirebaseRecyclerOptions<Message> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageViewHolder holder, int position, @NonNull Message model) {
        holder.setContent(model.getContent());
        holder.setUsername(model.getUsername());
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_single_chatroom, parent, false);
        return new MessageViewHolder(view);
    }
}
