package com.app.concertbud.concertbuddies.ViewFragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.concertbud.concertbuddies.R;


public class LocateEventFragment extends Fragment {

    private FragmentManager mFragManager;
    private FragmentTransaction mFragTransition;

    private MapFragment mapFragment;

    public LocateEventFragment() {
        // Required empty public constructor
    }

    public static LocateEventFragment newInstance() {
        LocateEventFragment fragment = new LocateEventFragment();
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
        return inflater.inflate(R.layout.fragment_locate_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    private void initView() {
        mFragManager = getChildFragmentManager();
        mFragTransition = mFragManager.beginTransaction();

        if (mapFragment == null)
            mapFragment = MapFragment.newInstance();

        mFragTransition.replace(R.id.fragment_container, mapFragment, "map_fragment");
        mFragTransition.commit();
    }
}
