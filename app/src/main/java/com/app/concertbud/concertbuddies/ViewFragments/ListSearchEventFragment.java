package com.app.concertbud.concertbuddies.ViewFragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.app.concertbud.concertbuddies.Activity.FindMatchActivity;
import com.app.concertbud.concertbuddies.Adapters.ChatRoomAdapter;
import com.app.concertbud.concertbuddies.Adapters.EventsAdapter;
import com.app.concertbud.concertbuddies.Helpers.AppUtils;
import com.app.concertbud.concertbuddies.Networking.Responses.CompleteConcertsResponse;
import com.app.concertbud.concertbuddies.Networking.Responses.ConcertResponse;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.Tasks.Configs.Jobs.FetchNearbyConcertsJob;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ListSearchEventFragment extends Fragment implements OnEventClickListener{
    @BindView(R.id.events_recycler)
    RecyclerView mEventRecycler;

    private String TAG = getTag();

    private EventsAdapter eventsAdapter;
    private Unbinder unbinder;

    /* Array List of all Nearby Concerts */
    ArrayList<ConcertResponse> mConcertsList;

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
            // TODO: initialize local position here ??
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

    private void initEventsRecycler() {
        eventsAdapter = new EventsAdapter(getContext(), this);

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
        AppUtils.startNewActivity(getContext(), getActivity(), FindMatchActivity.class);
    }

    @Override
    public void onEventLongClicked(int position) {

    }

    /***********
     * SUBSCRIBERS
     */

    @Subscribe
    public void onEvent(FetchNearbyConcertsJob request) {
        // update adapter
        Log.e(TAG, request.toString());
        eventsAdapter.notifyItemChanged(1);
    }
}
