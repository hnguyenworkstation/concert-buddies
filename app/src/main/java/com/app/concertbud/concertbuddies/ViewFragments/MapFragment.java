package com.app.concertbud.concertbuddies.ViewFragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.concertbud.concertbuddies.Abstracts.OnEventClickListener;
import com.app.concertbud.concertbuddies.Adapters.EventsCardAdapter;
import com.app.concertbud.concertbuddies.AppControllers.BaseApplication;
import com.app.concertbud.concertbuddies.EventBuses.ConcertsNearbyBus;
import com.app.concertbud.concertbuddies.EventBuses.DeliverLocationBus;
import com.app.concertbud.concertbuddies.EventBuses.DeliverPlaceBus;
import com.app.concertbud.concertbuddies.EventBuses.IsOnAnimationBus;
import com.app.concertbud.concertbuddies.Helpers.EventClusterRenderer;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.EventClusterItemEntity;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.EventsEntity;
import com.app.concertbud.concertbuddies.R;

import com.app.concertbud.concertbuddies.Tasks.Configs.Jobs.FetchNearbyConcertsJob;
import com.birbit.android.jobqueue.JobManager;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks,
        OnEventClickListener,
        ClusterManager.OnClusterInfoWindowClickListener<EventClusterItemEntity>,
        ClusterManager.OnClusterItemClickListener<EventClusterItemEntity>,
        ClusterManager.OnClusterClickListener<EventClusterItemEntity>,
        ClusterManager.OnClusterItemInfoWindowClickListener<EventClusterItemEntity> {
    // Cluster Information Views
    @BindView(R.id.bottom_sheet_list_clusters)
    LinearLayout mBottomSheetListClusters;
    @BindView(R.id.list_events)
    RecyclerView mBottomEventListRecycler;

    private Unbinder unbinder;
    private GoogleMap mMap;
    private Location lastKnownLocation;
    private GoogleApiClient mGoogleClient;
    private SupportMapFragment supportMapFragment;
    private FusedLocationProviderClient mFusedLocationClient;
    private final JobManager jobManager = BaseApplication.getInstance().getJobManager();
    private Place currentPlace;
    private String TAG = MapFragment.class.getSimpleName();
    private int mPosition;
    private ClusterManager<EventClusterItemEntity> clusterManager;

    private ArrayList<EventsEntity> mConcertsList = new ArrayList<>();
    private ArrayList<EventsEntity> mClusterConcertsList = new ArrayList<>();

    private EventsCardAdapter eventsCardAdapter;
    private BottomSheetBehavior clusterCardBehavior;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(int position) {
        MapFragment fragment = new MapFragment();
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
        /* Get current location */
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        initMap();
        initRideRecycler();
        initBottomListEventsDialog();
    }

    private void initRideRecycler() {
        eventsCardAdapter = new EventsCardAdapter(getContext(), mClusterConcertsList, this);

        final RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mBottomEventListRecycler.setLayoutManager(mLayoutManager);
        mBottomEventListRecycler.setItemAnimator(new DefaultItemAnimator());
        mBottomEventListRecycler.setNestedScrollingEnabled(true);
        mBottomEventListRecycler.setHasFixedSize(false);
        mBottomEventListRecycler.setAdapter(eventsCardAdapter);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mBottomEventListRecycler);
    }

    private void initBottomListEventsDialog() {
        clusterCardBehavior = BottomSheetBehavior.from(mBottomSheetListClusters);
        clusterCardBehavior.setHideable(true);
        clusterCardBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        clusterCardBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void initMap() {
        if (mGoogleClient == null) {
            mGoogleClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(AppIndex.API).build();
        }

        supportMapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map_fragment);
        supportMapFragment.getMapAsync(this);
    }

    private void updateMapView() {
        mMap.setPadding(24, 324, 24, 188);

        if (lastKnownLocation != null) {
            CameraPosition newPos = new CameraPosition.Builder()
                    .target(new LatLng(lastKnownLocation.getLatitude(),
                            lastKnownLocation.getLongitude()))
                    .zoom(14)
                    .build();

            addCustomMarkerToMap(getContext(), mMap, 56, 56, "Current Location",
                    new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()),
                    R.drawable.ic_maps_and_flags);

            EventBus.getDefault().post(new IsOnAnimationBus(true));
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(newPos), new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {
                    mMap.getUiSettings().setScrollGesturesEnabled(true);
                    EventBus.getDefault().post(new IsOnAnimationBus(false));
                }

                @Override
                public void onCancel() {
                    mMap.getUiSettings().setAllGesturesEnabled(true);
                    EventBus.getDefault().post(new IsOnAnimationBus(false));
                }
            });
        }
    }

    // this method will take a place location on a map and start animation to that location
    private void moveCameraToNewPlace(Place newPlace) {
        CameraPosition newPos = new CameraPosition.Builder()
                .target(newPlace.getLatLng())
                .zoom(14)
                .build();

        mMap.clear();
        addCustomMarkerToMap(getContext(), mMap, 56, 56, newPlace.getName().toString(),
                newPlace.getLatLng(), R.drawable.ic_maps_and_flags);

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(newPos), new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                mMap.getUiSettings().setAllGesturesEnabled(true);
            }

            @Override
            public void onCancel() {
                mMap.getUiSettings().setAllGesturesEnabled(true);
            }
        });
    }

    private void addCustomMarkerToMap(Context context, GoogleMap mMap, int width, int height,
                                      String title, LatLng loc, int drawable) {
        BitmapDrawable bitmapdraw = (BitmapDrawable) context.getResources().getDrawable(drawable);
        Bitmap bitmap = bitmapdraw.getBitmap();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);

        MarkerOptions marker = new MarkerOptions().position(loc).title(title);
        marker.icon(BitmapDescriptorFactory.fromBitmap(scaledBitmap));
        mMap.addMarker(marker);

        CircleOptions circle = new CircleOptions()
                .center(loc)
                .radius(800)
                .strokeColor(context.getResources().getColor(android.R.color.transparent))
                .fillColor(context.getResources().getColor(R.color.trans_accent));

        mMap.addCircle(circle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // get known location
        getLastKnowLocation();

        updateMapView();
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });

        initClusterManager();
    }

    private void initClusterManager() {
        clusterManager = new ClusterManager<EventClusterItemEntity>(getActivity(), mMap);
        clusterManager.setRenderer(new EventClusterRenderer(getContext(), mMap, clusterManager));

        mMap.setOnCameraIdleListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);
        mMap.setOnInfoWindowClickListener(clusterManager);

        clusterManager.setOnClusterClickListener(this);
        clusterManager.setOnClusterItemClickListener(this);
        clusterManager.setOnClusterInfoWindowClickListener(this);
        clusterManager.setOnClusterItemInfoWindowClickListener(this);
    }

    protected void showClusters(List<EventsEntity> eventsEntities) {
        clusterManager.clearItems();
        clusterManager.cluster();

        List<EventClusterItemEntity> entities = new ArrayList<>();

        for (EventsEntity eventsEntity : eventsEntities) {
            LatLng position = new LatLng(
                    Double.parseDouble(eventsEntity.getEmbedded().getVenues().get(0).getLocation().getLatitude()),
                    Double.parseDouble(eventsEntity.getEmbedded().getVenues().get(0).getLocation().getLongitude()));

            final EventClusterItemEntity clusterItemEntity = new EventClusterItemEntity(eventsEntity, position, eventsEntity.getName(),
                    eventsEntity.getEmbedded().getVenues().get(0).getAddress().getLine1());
            entities.add(clusterItemEntity);
        }

        clusterManager.addItems(entities);
        clusterManager.cluster();
    }

    /*
    * This method will get the height of current height on the recycler view which contains
    * all events card (in the clicked clusters) then minimize it to fit the screen
    * */
    private void updateClusterBottomSheetHeightAndExpand() {
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();

        int height = displayMetrics.heightPixels;
        int maxHeight = (int) (height * 0.38);

        int currentHeight = mBottomSheetListClusters.getLayoutParams().height;

        ViewGroup.LayoutParams layoutParams = mBottomSheetListClusters.getLayoutParams();
        layoutParams.height = currentHeight < maxHeight? currentHeight: maxHeight;
        mBottomSheetListClusters.setLayoutParams(layoutParams);

        eventsCardAdapter.notifyDataSetChanged();
        clusterCardBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        getLastKnowLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

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
     * UTILITIES
     ***************************************************************/
    @SuppressLint("MissingPermission")
    private void getLastKnowLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            lastKnownLocation = location;
                            EventBus.getDefault().postSticky(new DeliverLocationBus(location));
                            updateMapView();
                        }
                    }
                });
    }


    @Override
    public boolean onClusterClick(Cluster<EventClusterItemEntity> cluster) {
//        // Create the builder to collect all essential cluster items for the bounds.
//        LatLngBounds.Builder builder = LatLngBounds.builder();
//        for (ClusterItem item : cluster.getItems()) {
//            builder.include(item.getPosition());
//        }
//        // Get the LatLngBounds
//        final LatLngBounds bounds = builder.build();
//
//        // Animate camera to the bounds
//        try {
//            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        mClusterConcertsList.clear();

        for(EventClusterItemEntity item: cluster.getItems()) {
            mClusterConcertsList.add(item.getEntity());
        }

        updateClusterBottomSheetHeightAndExpand();

        return false;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<EventClusterItemEntity> cluster) {

    }

    @Override
    public boolean onClusterItemClick(EventClusterItemEntity eventClusterItemEntity) {
        mClusterConcertsList.clear();
        mClusterConcertsList.add(eventClusterItemEntity.getEntity());

        updateClusterBottomSheetHeightAndExpand();

        return false;
    }

    @Override
    public void onClusterItemInfoWindowClick(EventClusterItemEntity eventClusterItemEntity) {

    }


    /****************************************************************
     * LISTENING TO ALL THE SIGNAL INTO THIS FRAGMENT BY EVENT BUS
     * @UpdateMapPaddingBus: Update the map padding
     **/
    @Subscribe(sticky = true)
    public void onEvent(DeliverPlaceBus bus) {
        currentPlace = bus.getPlace();

        /* Fetch events nearby that location */
        Log.e(TAG, "fetching concerts for newly picked location");
        LatLng location = currentPlace.getLatLng();
        jobManager.addJobInBackground(new FetchNearbyConcertsJob(mPosition, location.longitude,
                location.latitude, true));

        /* Move dropped pin to newly picked location */
        moveCameraToNewPlace(currentPlace);

        EventBus.getDefault().removeStickyEvent(bus);
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(ConcertsNearbyBus bus) {
        if (bus.getToClass().equals(MapFragment.class.getSimpleName())) {
            /* check if there's still data left */
            if (bus.getConcerts().size() > 0) {
            /* Put all events in HashMap to make sure no duplicated event is allowed */
                HashMap<String, EventsEntity> mConcertsHashMap = new HashMap<>();
                for (int i = 0; i < bus.getConcerts().size(); i++) {
                    mConcertsHashMap.put(bus.getConcerts().get(i).getName(), bus.getConcerts().get(i));
                }
                Log.e(TAG, "Updating new concerts");
                mConcertsList.addAll(mConcertsHashMap.values());
                showClusters(mConcertsList);
            }
            EventBus.getDefault().removeStickyEvent(bus);
        }
    }

    @Override
    public void onEventClicked(int position) {

    }

    @Override
    public void onEventLongClicked(int position) {

    }
}