package com.app.concertbud.concertbuddies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.app.concertbud.concertbuddies.Abstracts.OnEventClickListener;
import com.app.concertbud.concertbuddies.Abstracts.OnLoadMoreListener;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.EventsEntity;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.ViewHolders.EventViewHolder;
import com.app.concertbud.concertbuddies.ViewHolders.LoadingViewHolder;

import java.util.ArrayList;

/**
 * Created by hungnguyen on 3/3/18.
 */

public class EventsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private OnEventClickListener listener;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading = false, isMoreDataAvailable = true;
    /* View Type */
    public final int TYPE_CONCERT = 0;
    public final int TYPE_LOADING = 1;

    /* Get a list of events */
    private ArrayList<EventsEntity> mConcertsList;

    public EventsAdapter(Context context, OnEventClickListener listener, ArrayList<EventsEntity> concerts) {
        this.context = context;
        this.listener = listener;
        this.mConcertsList = concerts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // TODO: return loading progress bar when necessary
        if (viewType == TYPE_CONCERT) {
            return new EventViewHolder(inflater.inflate(R.layout.event_viewholder, parent, false));
        }
        else {
            return new LoadingViewHolder(inflater.inflate(R.layout.layout_loading, parent, false));
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position >= getItemCount() - 1 && isMoreDataAvailable && !isLoading && onLoadMoreListener != null) {
            isLoading = true;
            onLoadMoreListener.onLoadMore();
        }

        if (!mConcertsList.isEmpty() && getItemViewType(position) == TYPE_CONCERT) {
            ((EventViewHolder)holder).init(position, listener, mConcertsList.get(position));
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        //holder.onRecycled();
    }

    @Override
    public int getItemViewType(int position) {
        if (!mConcertsList.get(position).getType().equals("loading")) {
            return TYPE_CONCERT;
        }
        else {
            return TYPE_LOADING;
        }
    }

    @Override
    public int getItemCount() {
        return mConcertsList.size();
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void notifyDataChanged() {
        notifyDataSetChanged();
        isLoading = false;
    }
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        this.isMoreDataAvailable = moreDataAvailable;
    }
}
