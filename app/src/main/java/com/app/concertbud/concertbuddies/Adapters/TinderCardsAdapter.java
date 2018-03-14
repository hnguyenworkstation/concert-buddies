package com.app.concertbud.concertbuddies.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.concertbud.concertbuddies.Networking.Responses.UserProfileResponse;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.ViewHolders.TinderCardViewHolder;

import java.util.List;

/**
 * Created by hungnguyen on 3/14/18.
 */
public class TinderCardsAdapter extends RecyclerView.Adapter<TinderCardViewHolder> {
    private List<UserProfileResponse> responseList;

    public TinderCardsAdapter(List<UserProfileResponse> responseList) {
        this.responseList = responseList;
    }

    @Override
    public TinderCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_tinder_card,
                parent, false);
        return new TinderCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TinderCardViewHolder holder, int position) {
        holder.init();
    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }
}
