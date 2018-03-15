package com.app.concertbud.concertbuddies.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.concertbud.concertbuddies.Abstracts.OnEventClickListener;
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
 * Created by hungnguyen on 3/3/18.
 */

public class EventViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.event_image)
    AdjustableImageView mEventImage;
    @BindView(R.id.event_progress)
    ProgressBar mEventProgress;
    @BindView(R.id.ride_time)
    TextView mRideTime;
    @BindView(R.id.ride_distance)
    TextView mRideDistance;
    @BindView(R.id.event_name)
    TextView mEventName;
    @BindView(R.id.event_location)
    TextView mEventLocation;

    private Unbinder unbinder;

    public EventViewHolder(View itemView) {
        super(itemView);

        unbinder = ButterKnife.bind(this, itemView);
    }

    public void init(int position, OnEventClickListener listener, EventsEntity concert) {
        unbinder = ButterKnife.bind(this, itemView);

        // Get Event Image
        ImageLoader.loadAdjustImageFromURL(mEventImage, concert.getImages().get(0).getUrl(), mEventProgress);
        // Get Event Name
        mEventName.setText(concert.getName());
        // Get Date
        String date = concert.getDates().getStart().getLocaldate();
        DateFormat df = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        DateFormat dpattern = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = dpattern.parse(date);
            String date_format = df.format(d);
            mRideTime.setText(date_format);
        } catch (ParseException e) {
            // handle exception
            mRideTime.setText(date.toString());
        }
        // Get Venue
        String venueName = concert.getEmbedded().getVenues().get(0).getName();
        mEventLocation.setText(venueName);

        // Get Distance
        Double distance = concert.getDistance();
        mRideDistance.setText(Double.toString(distance) + " miles");
    }

    public void onRecycled() {
        unbinder.unbind();
    }
}
