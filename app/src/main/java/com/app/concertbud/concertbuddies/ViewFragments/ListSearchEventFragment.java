package com.app.concertbud.concertbuddies.ViewFragments;


import android.content.Intent;
import android.location.Location;
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

import com.app.concertbud.concertbuddies.Abstracts.OnEventClickListener;
import com.app.concertbud.concertbuddies.Abstracts.OnLoadMoreListener;
import com.app.concertbud.concertbuddies.Activity.EventActivity;
import com.app.concertbud.concertbuddies.Activity.FindMatchActivity;
import com.app.concertbud.concertbuddies.Adapters.EventsAdapter;
import com.app.concertbud.concertbuddies.AppControllers.BaseApplication;
import com.app.concertbud.concertbuddies.EventBuses.ConcertsNearbyBus;
import com.app.concertbud.concertbuddies.EventBuses.DeliverLocationBus;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.EventsEntity;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.Tasks.Configs.Jobs.FetchNearbyConcertsJob;
import com.birbit.android.jobqueue.JobManager;

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
    protected Handler handler;
    /* Array List of all Nearby Concerts */
    private ArrayList<EventsEntity> mConcertsList = new ArrayList<>();
    private Location location;
    private int mPosition;
    private String TAG = ListSearchEventFragment.class.getSimpleName();
    private final JobManager jobManager = BaseApplication.getInstance().getJobManager();

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
        final RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mEventRecycler.setLayoutManager(mLayoutManager);
        mEventRecycler.setItemAnimator(new DefaultItemAnimator());
        mEventRecycler.setNestedScrollingEnabled(false);
        mEventRecycler.setHasFixedSize(false);

        handler = new Handler();

        /* Create an Object for adapter */
        Log.e(TAG, "Creating adapter");
        eventsAdapter = new EventsAdapter(getContext(), this, mConcertsList);
        eventsAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        loadMoreConcerts();
                    }
                });
            }
        });
        mEventRecycler.setAdapter(eventsAdapter);

        // TODO: check if mConcertList is empty to set visibility of view
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mEventRecycler);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onEventClicked(final int position) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getContext().startActivity(new Intent(getActivity(), EventActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("EventsEntity", mConcertsList.get(position)));
            }
        };
        new Handler().postDelayed(runnable, 0);
    }

    @Override
    public void onEventLongClicked(int position) {

    }

    /***********
     * UTILITIES
     ***********/
    void loadMoreConcerts() {
        Log.e(TAG, "loadMoreConcerts");
        mConcertsList.add(new EventsEntity("loading"));
        eventsAdapter.notifyItemInserted(mConcertsList.size() - 1);
        // Add job to load more concerts in the background
        jobManager.addJobInBackground(new FetchNearbyConcertsJob(mPosition,
                location.getLongitude(), location.getLatitude(), false));
    }
    /***********
     * SUBSCRIBERS
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(DeliverLocationBus bus) {
        location = bus.getLocation();
        EventBus.getDefault().removeStickyEvent(bus);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(ConcertsNearbyBus bus) {
        if (bus.getToClass().equals(ListSearchEventFragment.class.getSimpleName())) {
            /* remove progress item */
            if (!mConcertsList.isEmpty() && mConcertsList.get(mConcertsList.size() - 1).getType().equals("loading")) {
                mConcertsList.remove(mConcertsList.size() - 1);
                eventsAdapter.notifyItemRemoved(mConcertsList.size() - 1);
            }
            if (bus.isNewLocation()) {
                int size = mConcertsList.size();
                mConcertsList.clear();
                // TODO: reset scroll position (i.e. jump to top) when clearing all data
                eventsAdapter.notifyItemRangeRemoved(0, size);
            }
        /* check if there's still data left */
            if (bus.getConcerts().size() == 0) {
                eventsAdapter.setMoreDataAvailable(false);
            }
            else {
            /* Put all events in HashMap to make sure no duplicated event is allowed */
                HashMap<String, EventsEntity> mConcertsHashMap = new HashMap<>();
                for (int i = 0; i < bus.getConcerts().size(); i++) {
                    mConcertsHashMap.put(bus.getConcerts().get(i).getName(), bus.getConcerts().get(i));
                }
                Log.e(TAG, "Updating new concerts");
                mConcertsList.addAll(mConcertsHashMap.values());
                eventsAdapter.notifyDataChanged();
            }
            EventBus.getDefault().removeStickyEvent(bus);
        }
    }
}
