package com.app.concertbud.concertbuddies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.concertbud.concertbuddies.Abstracts.OnEventClickListener;
import com.app.concertbud.concertbuddies.Abstracts.OnLoadMoreListener;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.EventsEntity;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.ViewHolders.EventCardViewHolder;

import java.util.ArrayList;

/**
 * Created by hungnguyen on 4/8/18.
 */

public class EventsCardAdapter extends RecyclerView.Adapter<EventCardViewHolder> {
    private Context context;
    private OnEventClickListener listener;
    private ArrayList<EventsEntity> mConcertsList;

    public EventsCardAdapter(Context context, ArrayList<EventsEntity> mConcertsList, OnEventClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.mConcertsList = mConcertsList;
    }

    @Override
    public EventCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_event_card, parent, false);
        return new EventCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventCardViewHolder holder, int position) {
        holder.init(position, listener, mConcertsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mConcertsList.size();
    }


}
