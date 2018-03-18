package com.app.concertbud.concertbuddies.ViewFragments;


import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.concertbud.concertbuddies.Activity.MainActivity;
import com.app.concertbud.concertbuddies.AppControllers.BaseApplication;
import com.app.concertbud.concertbuddies.EventBuses.ConcertsNearbyBus;
import com.app.concertbud.concertbuddies.EventBuses.DeliverLocationBus;
import com.app.concertbud.concertbuddies.EventBuses.DeliverPlaceBus;
import com.app.concertbud.concertbuddies.EventBuses.TriggerViewBus;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.Tasks.Configs.Jobs.FetchNearbyConcertsJob;
import com.birbit.android.jobqueue.JobManager;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.xml.transform.Result;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class LocateEventFragment extends Fragment {
    @BindView(R.id.loc_name)
    TextView mLocationName;

    private FragmentManager mFragManager;
    private FragmentTransaction mFragTransition;

    private MapFragment mapFragment;
    private ListSearchEventFragment listSearchEventFragment;


    private final int PLACE_PICKER_REQUEST = 1;
    private int currentStage = 0;

    private static int mPostion;
    private String TAG = LocateEventFragment.class.getSimpleName();


    /* Location */
    private Location mLocation;

    /* Job Manager */
    private final JobManager jobManager = BaseApplication.getInstance().getJobManager();

    private Unbinder unbinder;

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
        unbinder = ButterKnife.bind(this, view);
        initView();
    }

    private void initView() {
        mFragManager = getChildFragmentManager();
        mFragTransition = mFragManager.beginTransaction();

        if (mapFragment == null)
            mapFragment = MapFragment.newInstance(mPostion);

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


    // This method will trigger place picker from google api with the default code is PLACE_PICKER_REQUEST
    // We can then catch the result of request code PLACE_PICKER_REQUEST
    private void triggerPlacePicker() throws GooglePlayServicesNotAvailableException,
            GooglePlayServicesRepairableException {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
    }


    @OnClick(R.id.search_area)
    public void onSearchAreaClicked() {
        try {
            triggerPlacePicker();
        } catch (GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException e) {
            Toast.makeText(getContext(), "Google Play Service not found!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(getContext(), data);
                String toastMsg = String.format("Place Picked: %s", place.getName());
                Toast.makeText(getContext(), toastMsg, Toast.LENGTH_LONG).show();

                mLocationName.setText(place.getName());

                EventBus.getDefault().postSticky(new DeliverPlaceBus(place));
            }
        }
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
        Log.e(TAG, "fetching concerts");
        jobManager.addJobInBackground(new FetchNearbyConcertsJob(mPostion, mLocation.getLongitude(),
                mLocation.getLatitude()));
    }

}
