package com.app.concertbud.concertbuddies.ViewFragments;


import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.concertbud.concertbuddies.AppControllers.BaseApplication;
import com.app.concertbud.concertbuddies.EventBuses.DeliverLocationBus;
import com.app.concertbud.concertbuddies.EventBuses.TriggerViewBus;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.Tasks.Configs.Jobs.FetchNearbyConcertsJob;
import com.birbit.android.jobqueue.JobManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class LocateEventFragment extends Fragment {

    private FragmentManager mFragManager;
    private FragmentTransaction mFragTransition;

    private MapFragment mapFragment;
    private ListSearchEventFragment listSearchEventFragment;

    private int currentStage = 0;

    private static int mPostion;
    /* Location */
    private Location mLocation;

    /* Job Manager */
    private final JobManager jobManager = BaseApplication.getInstance().getJobManager();

    public LocateEventFragment() {
        // Required empty public constructor
    }

    public static LocateEventFragment newInstance(int position) {
        LocateEventFragment fragment = new LocateEventFragment();
        Bundle args = new Bundle();
        mPostion = position;
        args.putInt("page_position", position);
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

        if (listSearchEventFragment == null)
            listSearchEventFragment = ListSearchEventFragment.newInstance(mPostion);

        mFragTransition.replace(R.id.fragment_container, mapFragment, "map_fragment");
        mFragTransition.commit();
    }

    private void switchView() {
        mFragTransition = mFragManager.beginTransaction();

        if (mFragManager.findFragmentByTag("list_fragment") != null) {
            mFragTransition.setCustomAnimations(R.anim.flip_right_in,
                    R.anim.flip_right_out,
                    R.anim.flip_left_in,
                    R.anim.flip_left_out)
                    .replace(R.id.fragment_container, mapFragment, "map_fragment")
                    .commit();
        } else {
            mFragTransition.setCustomAnimations(R.anim.flip_right_in,
                    R.anim.flip_right_out,
                    R.anim.flip_left_in,
                    R.anim.flip_left_out)
                    .replace(R.id.fragment_container, listSearchEventFragment, "list_fragment")
                    .commit();
        }
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


    /****************************************************************
     * LISTENING TO ALL THE SIGNAL INTO THIS FRAGMENT BY EVENT BUS
     * @UpdateMapPaddingBus: Update the map padding
     **/
    @Subscribe
    public void onEvent(TriggerViewBus bus) {
        if (bus.getViewCode() != currentStage) {
            switchView();
            currentStage = bus.getViewCode();
        }
    }

    @Subscribe
    public void onEvent(DeliverLocationBus location) {
        mLocation = location.getLocation();
        jobManager.addJobInBackground(new FetchNearbyConcertsJob(0,
                mLocation.getLongitude(), mLocation.getLatitude()));
    }
}
