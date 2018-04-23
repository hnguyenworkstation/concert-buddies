package com.app.concertbud.concertbuddies.Adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.app.concertbud.concertbuddies.Abstracts.OnSubscribedEventClickListener;
import com.app.concertbud.concertbuddies.Networking.Responses.CompleteTMConcertsResponse;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.EventsEntity;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.ImagesEntity;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.NestedEmbeddedVenuesEntity;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.StartEntity;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.ViewHolders.SubscribedEventViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.List;

/**
 * Created by hungnguyen on 4/10/18.
 */

public class SubscribedEventsAdapter extends RecyclerView.Adapter<SubscribedEventViewHolder> {
    private Context context;
    private List<EventsEntity> eventList;
    private OnSubscribedEventClickListener listener;

    public SubscribedEventsAdapter(Context context, List<EventsEntity> eventList,
                                   OnSubscribedEventClickListener listener) {
        this.context = context;
        this.eventList = eventList;
        this.listener = listener;
    }

    @Override
    public SubscribedEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_event_card, parent, false);
        return new SubscribedEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubscribedEventViewHolder holder, int i) {
        EventsEntity event = eventList.get(i);
        Log.d("chris", event.getId() + "");
        if (event.getId() == null) {
            Log.d("chris",
                    "error, we should not be printing empty events dog (Join Event Page)");
            return;
        }
        final View iView = holder.itemView;
        NestedEmbeddedVenuesEntity venue = event.getEmbedded().getVenues().get(0);

        final int position = i;

        iView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEventClicked(position);
            }
        });

        String location;
        String city = venue.getCity().getName();
        if (venue.getState() != null) {
            String state = venue.getState().getStatecode();
            location = city + ", " + state;
        } else {
            location = city + ", " + venue.getCountry().getCountrycode();
        }

        if (event.getImages() != null) {
            AppCompatImageView imgView = iView.findViewById(R.id.event_image);
            ImagesEntity img = event.getImages().get(2);

            Picasso.get()
                .load(img.getUrl())
                .fit()
                .centerCrop()
                .into(imgView, new Callback() {
                    @Override
                    public void onSuccess() {
                        iView.findViewById(R.id.event_progress).setVisibility(View.GONE);
                    }
                    @Override
                    public void onError(Exception e) {
                        Log.e("SubcribedEventsAdapter", e.getMessage());
                    }
                });

        } else {
            // TODO: SET DEFAULT EVENT IMG
        }

        ((TextView)iView.findViewById(R.id.event_name)).setText(event.getName());
        ((TextView)iView.findViewById(R.id.event_venue)).setText("at " + venue.getName());
        ((TextView)iView.findViewById(R.id.event_desc)).setText(event.getInfo());
        ((TextView)iView.findViewById(R.id.event_location)).setText(location);

        StartEntity start = event.getDates().getStart();
        ((TextView)iView.findViewById(R.id.event_date)).setText(start.getLocaldate().replace('-', '/'));
        try {
            SimpleDateFormat in_format = new SimpleDateFormat("HH:MM:SS");
            SimpleDateFormat out_format = new SimpleDateFormat("h:mm a");
            Date parsed = in_format.parse(start.getLocaltime());
            ((TextView)iView.findViewById(R.id.event_time)).setText(out_format.format(parsed));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
