package com.app.concertbud.concertbuddies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.concertbud.concertbuddies.Abstracts.OnChatRoomClickListener;
import com.app.concertbud.concertbuddies.Abstracts.OnEventClickListener;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.ViewHolders.ChatRoomViewHolder;
import com.app.concertbud.concertbuddies.ViewHolders.EventViewHolder;

/**
 * Created by hungnguyen on 3/3/18.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventViewHolder> {
    private Context context;
    private OnEventClickListener listener;

    public EventsAdapter(Context context, OnEventClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_viewholder,
                        parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        holder.init(position, listener);
    }

    @Override
    public void onViewRecycled(EventViewHolder holder) {
        super.onViewRecycled(holder);
        holder.onRecycled();
    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
