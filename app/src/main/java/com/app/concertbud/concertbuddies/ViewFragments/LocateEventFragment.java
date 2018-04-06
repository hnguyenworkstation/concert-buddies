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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.concertbud.concertbuddies.Activity.MainActivity;
import com.app.concertbud.concertbuddies.AppControllers.BaseApplication;
import com.app.concertbud.concertbuddies.EventBuses.ConcertsNearbyBus;
import com.app.concertbud.concertbuddies.EventBuses.DeliverLocationBus;
import com.app.concertbud.concertbuddies.EventBuses.DeliverPlaceBus;
import com.app.concertbud.concertbuddies.EventBuses.TriggerViewBus;
import com.app.concertbud.concertbuddies.Helpers.ImageLoader;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.Tasks.Configs.Jobs.FetchNearbyConcertsJob;
import com.birbit.android.jobqueue.JobManager;
import com.facebook.Profile;
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

    @BindView(R.id.search_bar)
    RelativeLayout mSearchBar;
    @BindView(R.id.profile_image)
    ImageView mProfileImage;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

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
    private boolean isShowingList = false;

    public static final int MAP_VIEW_CODE = 0;
    public static final int LIST_VIEW_CODE = 1;

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
        ImageLoader.loadSimpleCircleImage(mProfileImage,
                Profile.getCurrentProfile().getProfilePictureUri(248,248).toString(),
                mProgressBar);

        mFragManager = getChildFragmentManager();
        mFragManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Log.e(TAG, "onBackStackChanged: " + mFragManager.getBackStackEntryCount());
                if (mFragManager.getBackStackEntryCount() == 1) {
                    mSearchBar.setVisibility(View.VISIBLE);
                }
            }
        });
        mFragTransition = mFragManager.beginTransaction();

        if (mapFragment == null)
            mapFragment = MapFragment.newInstance(mPostion);

        if (listSearchEventFragment == null)
            listSearchEventFragment = ListSearchEventFragment.newInstance(mPostion);

        mFragTransition.replace(R.id.fragment_container, mapFragment, "map_fragment");
        mFragTransition.addToBackStack(null);
        mFragTransition.commit();
    }

    private void flipMap() {
        if (isShowingList) {
            getFragmentManager().popBackStack();
            isShowingList = false;
            return;
        }

        // Flip to the back.
        isShowingList = true;

        // Create and commit a new fragment transaction that adds the fragment for
        // the back of the card, uses custom animations, and is part of the fragment
        // manager's back stack.

        getFragmentManager()
                .beginTransaction()

                // Replace the default fragment animations with animator resources
                // representing rotations when switching to the back of the card, as
                // well as animator resources representing rotations when flipping
                // back to the front (e.g. when the system Back button is pressed).
                .setCustomAnimations(
                        R.anim.card_flip_right_in,
                        R.anim.card_flip_right_out,
                        R.anim.card_flip_left_in,
                        R.anim.card_flip_left_out)

                // Replace any fragments currently in the container view with a
                // fragment representing the next page (indicated by the
                // just-incremented currentPage variable).
                .replace(R.id.fragment_container, listSearchEventFragment)

                // Add this transaction to the back stack, allowing users to press
                // Back to get to the front of the card.
                .addToBackStack(null)

                // Commit the transaction.
                .commit();
    }


    // This method will trigger place picker from google api with the default code is PLACE_PICKER_REQUEST
    // We can then catch the result of request code PLACE_PICKER_REQUEST
    private void triggerPlacePicker() throws GooglePlayServicesNotAvailableException,
            GooglePlayServicesRepairableException {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
    }


    @OnClick(R.id.search_bar)
    public void onSearchAreaClicked() {
        try {
            triggerPlacePicker();
        } catch (GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException e) {
            Toast.makeText(getContext(), "Google Play Service not found!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @OnClick(R.id.switch_action)
    public void onSwitchMapClicked() {
        flipMap();
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
        if (isShowingList && bus.getViewCode() == MAP_VIEW_CODE) {
            flipMap();
        } else if (!isShowingList && bus.getViewCode() == LIST_VIEW_CODE) {
            flipMap();
        }
    }

    @Subscribe
    public void onEvent(DeliverLocationBus location) {
        mLocation = location.getLocation();
        Log.e(TAG, "fetching concerts");
        jobManager.addJobInBackground(new FetchNearbyConcertsJob(mPostion, mLocation.getLongitude(),
                mLocation.getLatitude(), false));
    }
}
