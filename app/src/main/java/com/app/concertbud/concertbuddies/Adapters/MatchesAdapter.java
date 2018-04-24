package com.app.concertbud.concertbuddies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.concertbud.concertbuddies.Abstracts.OnMatchClickListener;
import com.app.concertbud.concertbuddies.Networking.Responses.MatchProfileResponse;
import com.app.concertbud.concertbuddies.Networking.Responses.MatchResponse;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.ViewHolders.EventCardViewHolder;
import com.app.concertbud.concertbuddies.ViewHolders.MatchViewHolder;

import java.util.List;

/**
 * Created by hungnguyen on 4/22/18.
 */

public class MatchesAdapter extends RecyclerView.Adapter<MatchViewHolder> {
    private Context context;
    private List<MatchProfileResponse> matchResponseList;
    private OnMatchClickListener listener;

    public MatchesAdapter(Context context, List<MatchProfileResponse> matchResponseList, OnMatchClickListener listener) {
        this.context = context;
        this.matchResponseList = matchResponseList;
        this.listener = listener;
    }

    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_simple_match_profile,
                parent, false);
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MatchViewHolder holder, int position) {
        holder.init(matchResponseList.get(position));
    }

    @Override
    public int getItemCount() {
        return matchResponseList.size();
    }

    @Override
    public void onViewRecycled(MatchViewHolder holder) {
        super.onViewRecycled(holder);
        holder.onRecycled();
    }
}
