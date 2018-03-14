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

    public void init(final int position, final OnEventClickListener listener) {
        unbinder = ButterKnife.bind(this, itemView);

        ImageLoader.loadAdjustImageFromURL(mEventImage, "https://consequenceofsound.files.wordpress.com/2018/01/bsoton-calling-2018.png?w=807", mEventProgress);
        mEventImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEventClicked(position);
            }
        });
    }


    public void onRecycled() {
        unbinder.unbind();
    }
}
