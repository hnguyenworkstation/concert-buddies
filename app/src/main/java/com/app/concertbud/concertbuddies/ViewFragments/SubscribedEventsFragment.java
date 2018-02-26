package com.app.concertbud.concertbuddies.ViewFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.concertbud.concertbuddies.R;


public class SubscribedEventsFragment extends Fragment {
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

}
