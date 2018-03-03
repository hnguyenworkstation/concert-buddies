package com.app.concertbud.concertbuddies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.concertbud.concertbuddies.Abstracts.OnChatRoomClickListener;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.ViewHolders.ChatRoomViewHolder;

/**
 * Created by hungnguyen on 3/3/18.
 */
public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomViewHolder> {
    private Context context;
    private OnChatRoomClickListener listener;

    public ChatRoomAdapter(Context context, OnChatRoomClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ChatRoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_room_viewholder,
                        parent, false);
        return new ChatRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatRoomViewHolder holder, int position) {
        holder.init(position, listener);
    }

    @Override
    public void onViewRecycled(ChatRoomViewHolder holder) {
        super.onViewRecycled(holder);
        holder.onRecycled();
    }

    @Override
    public int getItemCount() {
        return 20;
    }
}