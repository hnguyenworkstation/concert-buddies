package com.app.concertbud.concertbuddies.ViewFragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.concertbud.concertbuddies.Abstracts.OnSubscribedEventClickListener;
import com.app.concertbud.concertbuddies.Activity.EventActivity;
import com.app.concertbud.concertbuddies.Adapters.SubscribedEventsAdapter;
import com.app.concertbud.concertbuddies.AppControllers.BaseApplication;
import com.app.concertbud.concertbuddies.Helpers.AppUtils;
import com.app.concertbud.concertbuddies.Helpers.DataUtils;
import com.app.concertbud.concertbuddies.Networking.NetContext;
import com.app.concertbud.concertbuddies.Networking.Requests.NewUserRequest;
import com.app.concertbud.concertbuddies.Networking.Responses.CompleteTMConcertsResponse;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.EventsEntity;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.ListEventsEntity;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.StartEntity;
import com.app.concertbud.concertbuddies.Networking.Services.BackendServices;
import com.app.concertbud.concertbuddies.Networking.Services.EventServices;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.Tasks.Configs.Jobs.FetchMatchesTask;
import com.app.concertbud.concertbuddies.Tasks.Configs.Jobs.FetchNearbyConcertsJob;
import com.app.concertbud.concertbuddies.Tasks.Configs.Jobs.GetEventJob;
import com.birbit.android.jobqueue.JobManager;
import com.facebook.AccessToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscribedEventsFragment extends Fragment implements OnSubscribedEventClickListener {
    @BindView(R.id.events_recycler)
    RecyclerView mEventsRecycler;
    @BindView(R.id.subcribed_events_refresh)
    SwipeRefreshLayout subscribedEventsRefreshLayout;
    @BindView(R.id.empty_message)
    TextView mEmptyMessage;

    public static SubscribedEventsFragment self;
    private final JobManager jobManager = BaseApplication.getInstance().getJobManager();

    private Unbinder unbinder;
    private SubscribedEventsAdapter eventsAdapter;
    private List<EventsEntity> events = new ArrayList<>();

    public SubscribedEventsFragment() {
        // Required empty public constructor
        self = this;
    }

    public static SubscribedEventsFragment newInstance() {
        SubscribedEventsFragment fragment = new SubscribedEventsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    boolean initial = true;
    @Override
    public void onResume() {
        super.onResume();
        if (!initial) {
            loadEvents();
        } else {
            initial = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subscribed_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initEventsRecycler();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    private void initEventsRecycler() {
        eventsAdapter = new SubscribedEventsAdapter(getContext(), events, this);

        final RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mEventsRecycler.setLayoutManager(mLayoutManager);
        mEventsRecycler.setItemAnimator(new DefaultItemAnimator());
        mEventsRecycler.setNestedScrollingEnabled(false);
        mEventsRecycler.setHasFixedSize(false);
        mEventsRecycler.setAdapter(eventsAdapter);

        // TODO: check if mConcertList is empty to set visibility of view
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mEventsRecycler);

        subscribedEventsRefreshLayout = getView().findViewById(R.id.subcribed_events_refresh);
        subscribedEventsRefreshLayout.setDistanceToTriggerSync(600);
        subscribedEventsRefreshLayout.setProgressViewEndTarget(false, 300);
        subscribedEventsRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadEvents();
            }
        });


        loadEvents();
    }

    @Override
    public void onEventClicked(final int position) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getContext().startActivity(new Intent(getActivity(), EventActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("EventsEntity", events.get(position)));
            }
        };
        new Handler().postDelayed(runnable, 0);
    }

    public void loadEvents() {
        subscribedEventsRefreshLayout.setRefreshing(true);
        EventServices services = NetContext.instance.create(EventServices.class);
        services.getEvents(AccessToken.getCurrentAccessToken().getUserId())
                .enqueue(new Callback<ListEventsEntity>() {
                    @Override
                    public void onResponse(Call<ListEventsEntity> call, Response<ListEventsEntity> response) {
                        List<String> eventIds = response.body().getEventIds();
                        max_events = eventIds.size();
                        Log.d("chris", max_events + " max");
                        added = 0;
                        while (events.size() < max_events) {
                            events.add(new EventsEntity(""));
                        }
                        while (events.size() > max_events) {
                            events.remove(events.size()-1);
                        }
                        for (int i = 0; i < max_events; i++) {
                            jobManager.addJobInBackground(new GetEventJob(eventIds.get(i), i));
                        }
                        if (max_events == 0) {
                            subscribedEventsRefreshLayout.setRefreshing(false);
                            eventsAdapter.notifyDataSetChanged();
                            mEmptyMessage.setVisibility(View.VISIBLE);
                        } else {
                            mEmptyMessage.setVisibility(View.GONE);
                        }

                        // Store data temporary
                        DataUtils.setSubscribedEvent(events);
                    }

                    @Override
                    public void onFailure(Call<ListEventsEntity> call, Throwable t) {

                    }
                });
    }

    private int max_events;
    private int added;
    synchronized
    public void addEventCard(EventsEntity event, int index) {
        // Sending signal right away to find match of this event
        events.set(index, event);
        if (++added == max_events) {
            Log.d("chris", "yippie");

            // sort by date
            Collections.sort(events, new Comparator<EventsEntity>() {
                @Override
                public int compare(EventsEntity e1, EventsEntity e2) {
                    StartEntity start = e1.getDates().getStart();
                    String dateStr1 = e1.getDates().getStart().getLocaldate();
                    String dateStr2 = e2.getDates().getStart().getLocaldate();

                    try {
                        SimpleDateFormat in_format = new SimpleDateFormat("yyyy-mm-dd");
                        Date date1 = in_format.parse(dateStr1);
                        Date date2 = in_format.parse(dateStr2);
                        return date1.compareTo(date2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });

            eventsAdapter.notifyDataSetChanged();
            subscribedEventsRefreshLayout.setRefreshing(false);
        }
        Log.d("chris", added + " added " + event.getName());
    }
}
