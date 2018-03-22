package com.app.concertbud.concertbuddies.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.app.concertbud.concertbuddies.R;

/**
 * Created by huongnguyen on 3/18/18.
 */

public class LoadingViewHolder extends RecyclerView.ViewHolder{
    public ProgressBar progressBar;
    public LoadingViewHolder(View itemView) {
        super(itemView);
        this.progressBar = itemView.findViewById(R.id.event_progress);
    }
}
