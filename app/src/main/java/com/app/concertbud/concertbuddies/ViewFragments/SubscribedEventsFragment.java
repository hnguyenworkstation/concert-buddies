package com.app.concertbud.concertbuddies.ViewFragments;


import android.os.Bundle;
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

import com.app.concertbud.concertbuddies.Abstracts.OnSubscribedEventClickListener;
import com.app.concertbud.concertbuddies.Adapters.SubscribedEventsAdapter;
import com.app.concertbud.concertbuddies.AppControllers.BaseApplication;
import com.app.concertbud.concertbuddies.Networking.NetContext;
import com.app.concertbud.concertbuddies.Networking.Requests.NewUserRequest;
import com.app.concertbud.concertbuddies.Networking.Responses.CompleteTMConcertsResponse;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.EventsEntity;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.ListEventsEntity;
import com.app.concertbud.concertbuddies.Networking.Services.BackendServices;
import com.app.concertbud.concertbuddies.Networking.Services.EventServices;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.Tasks.Configs.Jobs.FetchNearbyConcertsJob;
import com.app.concertbud.concertbuddies.Tasks.Configs.Jobs.GetEventJob;
import com.birbit.android.jobqueue.JobManager;
import com.facebook.AccessToken;

import java.util.ArrayList;
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
        subscribedEventsRefreshLayout.setProgressViewEndTarget(true, 300);
        subscribedEventsRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadEvents();
            }
        });
        loadEvents();
    }

    @Override
    public void onEventClicked(int position) {
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
                        added = 0;
                        if (max_events == 0) {
                            subscribedEventsRefreshLayout.setRefreshing(false);
                        }
                        while (events.size() < max_events) {
                            events.add(new EventsEntity(""));
                        }
                        for (int i = 0; i < max_events; i++) {
                            jobManager.addJobInBackground(new GetEventJob(eventIds.get(i), i));
                        }
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
        events.set(index, event);
        if (++added == max_events) {
            eventsAdapter.notifyDataSetChanged();
            subscribedEventsRefreshLayout.setRefreshing(false);
        }
    }
}
