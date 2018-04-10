package com.app.concertbud.concertbuddies.ViewFragments;


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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.concertbud.concertbuddies.Abstracts.OnLoadMoreListener;
import com.app.concertbud.concertbuddies.Abstracts.OnSubscribedEventClickListener;
import com.app.concertbud.concertbuddies.Adapters.EventsAdapter;
import com.app.concertbud.concertbuddies.Adapters.SubscribedEventsAdapter;
import com.app.concertbud.concertbuddies.Networking.Responses.SubscribedEventResponse;
import com.app.concertbud.concertbuddies.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SubscribedEventsFragment extends Fragment implements OnSubscribedEventClickListener {
    @BindView(R.id.events_recycler)
    RecyclerView mEventsRecycler;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private Unbinder unbinder;
    private SubscribedEventsAdapter eventsAdapter;
    private List<SubscribedEventResponse> eventResponseList = new ArrayList<>();

    public SubscribedEventsFragment() {
        // Required empty public constructor
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
        if (getArguments() != null) {
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
        eventsAdapter = new SubscribedEventsAdapter(getContext(), eventResponseList, this);

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
    }

    @Override
    public void onEventClicked(int position) {

    }
}
