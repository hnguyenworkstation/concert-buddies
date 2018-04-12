package com.app.concertbud.concertbuddies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.concertbud.concertbuddies.Abstracts.OnSubscribedEventClickListener;
import com.app.concertbud.concertbuddies.Networking.Responses.SubscribedEventResponse;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.ViewHolders.EventCardViewHolder;
import com.app.concertbud.concertbuddies.ViewHolders.SubscribedEventViewHolder;

import java.util.List;

/**
 * Created by hungnguyen on 4/10/18.
 */

public class SubscribedEventsAdapter extends RecyclerView.Adapter<SubscribedEventViewHolder> {
    private Context context;
    private List<SubscribedEventResponse> subscribedEventResponseList;
    private OnSubscribedEventClickListener listener;

    public SubscribedEventsAdapter(Context context, List<SubscribedEventResponse> subscribedEventResponseList,
                                   OnSubscribedEventClickListener listener) {
        this.context = context;
        this.subscribedEventResponseList = subscribedEventResponseList;
        this.listener = listener;
    }

    @Override
    public SubscribedEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_event_card, parent, false);
        return new SubscribedEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubscribedEventViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
