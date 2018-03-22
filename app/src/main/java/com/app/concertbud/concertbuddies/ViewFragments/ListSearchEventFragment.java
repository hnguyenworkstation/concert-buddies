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

import com.app.concertbud.concertbuddies.Abstracts.OnEventClickListener;
import com.app.concertbud.concertbuddies.Adapters.EventsAdapter;
import com.app.concertbud.concertbuddies.EventBuses.ConcertsNearbyBus;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.EventsEntity;
import com.app.concertbud.concertbuddies.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ListSearchEventFragment extends Fragment implements OnEventClickListener {
    @BindView(R.id.events_recycler)
    RecyclerView mEventRecycler;

    private EventsAdapter eventsAdapter;
    private Unbinder unbinder;
    /* Array List of all Nearby Concerts */
    private ArrayList<EventsEntity> mConcertsList = new ArrayList<>();
    private int mPosition;
    private String TAG = ListSearchEventFragment.class.getSimpleName();

    public ListSearchEventFragment() {
        // Required empty public constructor
    }

    public static ListSearchEventFragment newInstance(int position) {
        ListSearchEventFragment fragment = new ListSearchEventFragment();
        Bundle args = new Bundle();
        args.putInt("page_position", position + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt("page_position");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_search_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        initEventsRecycler();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initEventsRecycler() {
        eventsAdapter = new EventsAdapter(getContext(), this, mConcertsList);

        final RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mEventRecycler.setLayoutManager(mLayoutManager);
        mEventRecycler.setItemAnimator(new DefaultItemAnimator());
        mEventRecycler.setNestedScrollingEnabled(false);
        mEventRecycler.setHasFixedSize(false);
        mEventRecycler.setAdapter(eventsAdapter);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mEventRecycler);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onEventClicked(int position) {

    }

    @Override
    public void onEventLongClicked(int position) {

    }

    /***********
     * SUBSCRIBERS
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(ConcertsNearbyBus bus) {
        /* Put all events in HashMap to make sure no duplicated event is allowed */
        HashMap<String, EventsEntity> mConcertsHashMap = new HashMap<>();
        for (int i = 0; i < bus.getConcerts().size(); i++) {
            mConcertsHashMap.put(bus.getConcerts().get(i).getName(), bus.getConcerts().get(i));
        }
        Log.e(TAG, "Updating new concerts");
        // TODO: ?? Double check if we need to clear mConcertsList
        mConcertsList.clear();
        mConcertsList.addAll(mConcertsHashMap.values());
        eventsAdapter.notifyItemChanged(mPosition);
        EventBus.getDefault().removeStickyEvent(bus);
    }
}
