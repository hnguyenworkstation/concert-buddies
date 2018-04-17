package com.app.concertbud.concertbuddies.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.concertbud.concertbuddies.Abstracts.OnEventClickListener;
import com.app.concertbud.concertbuddies.Abstracts.OnSubscribedEventClickListener;
import com.app.concertbud.concertbuddies.CustomUI.AdjustableImageView;
import com.app.concertbud.concertbuddies.Helpers.ImageLoader;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.EventsEntity;
import com.app.concertbud.concertbuddies.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hungnguyen on 4/10/18.
 */

public class SubscribedEventViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.root_view)
    LinearLayout mRootView;
    @BindView(R.id.event_image)
    AdjustableImageView mEventImage;
    @BindView(R.id.event_progress)
    ProgressBar mEventProgress;
    @BindView(R.id.event_time)
    TextView mEventTime;
    @BindView(R.id.event_distance)
    TextView mEventDistance;
    @BindView(R.id.event_name)
    TextView mEventName;
    @BindView(R.id.event_location)
    TextView mEventLocation;
    @BindView(R.id.event_venue)
    TextView mEventVenue;

    private Unbinder unbinder;

    public SubscribedEventViewHolder(View itemView) {
        super(itemView);
    }

    public void init(final int position, final OnSubscribedEventClickListener listener, EventsEntity concert) {
        unbinder = ButterKnife.bind(this, itemView);

        // Get Event Image
        // Only choose one whose width is 500px or larger
        boolean edited = false;
        for (int i = 0; i < concert.getImages().size(); i++) {
            if (concert.getImages().get(i).getWidth() > 500) {
                ImageLoader.loadAdjustImageFromURL(mEventImage, concert.getImages().get(i).getUrl(), mEventProgress);
                edited = true;
                break;
            }
        }
        if (!edited) {
            ImageLoader.loadAdjustImageFromURL(mEventImage, concert.getImages().get(0).getUrl(), mEventProgress);
        }
        // Get Event Name
        mEventName.setText(concert.getName());
        // Get Date
        String date = concert.getDates().getStart().getLocaldate();
        DateFormat df = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        DateFormat dpattern = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = dpattern.parse(date);
            String date_format = df.format(d);
            mEventTime.setText(date_format);
        } catch (ParseException e) {
            // handle exception
            mEventTime.setText(date.toString());
        }
        // Get Venue
        String venueName = concert.getEmbedded().getVenues().get(0).getName();
        mEventLocation.setText(venueName);

        // Get Distance
        Double distance = concert.getDistance();
        mEventDistance.setText(Long.toString(Math.round(distance)) + " miles");


        // Init OnClicklistener
        mRootView.setOnClickListener(new View.OnClickListener() {
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
