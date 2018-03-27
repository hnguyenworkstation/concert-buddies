package com.app.concertbud.concertbuddies.Activity;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.concertbud.concertbuddies.AppControllers.BaseActivity;
import com.app.concertbud.concertbuddies.CustomUI.AdjustableImageView;
import com.app.concertbud.concertbuddies.Helpers.ImageLoader;
import com.app.concertbud.concertbuddies.Helpers.MapUtils;
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

public class EventActivity extends BaseActivity {
    // Event Card Profile
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

    // Event Map Location
    @BindView(R.id.map_image)
    AdjustableImageView mMapImage;
    @BindView(R.id.map_progress_bar)
    ProgressBar mMapProgressBar;

    private Unbinder unbinder;
    private EventsEntity concert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        unbinder = ButterKnife.bind(this);

        concert = (EventsEntity) getIntent().getSerializableExtra("EventsEntity");

        if (concert != null) {
            initConcertView();
        }
    }


    /*
    * Draw every details of the event entity
    * Using Huong'code from event entity viewholder
    * */
    private void initConcertView() {
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

        // Load Map Image
        ImageLoader.loadAdjustImageFromURL(mMapImage, MapUtils.getMapLocationUrl(concert.getEmbedded().getVenues().get(0).getLocation().getLatitude(),
                concert.getEmbedded().getVenues().get(0).getLocation().getLongitude()), mMapProgressBar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
