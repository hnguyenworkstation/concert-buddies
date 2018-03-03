package com.app.concertbud.concertbuddies.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.app.concertbud.concertbuddies.Abstracts.OnEventClickListener;
import com.app.concertbud.concertbuddies.CustomUI.AdjustableImageView;
import com.app.concertbud.concertbuddies.Helpers.ImageLoader;
import com.app.concertbud.concertbuddies.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hungnguyen on 3/3/18.
 */

public class EventViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.event_image)
    AdjustableImageView mEventImage;
    @BindView(R.id.event_progress)
    ProgressBar mEventProgress;

    private Unbinder unbinder;

    public EventViewHolder(View itemView) {
        super(itemView);

        unbinder = ButterKnife.bind(this, itemView);
    }

    public void init(int position, OnEventClickListener listener) {
        unbinder = ButterKnife.bind(this, itemView);

        ImageLoader.loadAdjustImageFromURL(mEventImage, "https://consequenceofsound.files.wordpress.com/2018/01/bsoton-calling-2018.png?w=807", mEventProgress);
    }


    public void onRecycled() {
        unbinder.unbind();
    }
}
