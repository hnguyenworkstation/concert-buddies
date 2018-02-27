package com.app.concertbud.concertbuddies.ViewFragments;


import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.concertbud.concertbuddies.R;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.Executor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private Unbinder unbinder;
    private GoogleMap mMap;
    private Location lastKnownLocation;
    private GoogleApiClient mGoogleClient;
    private SupportMapFragment supportMapFragment;
    private FusedLocationProviderClient mFusedLocationClient;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

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
        mMap.setPadding(24, 124, 24, 124);

        if (lastKnownLocation != null) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(lastKnownLocation.getLatitude(),
                    lastKnownLocation.getLongitude()))
                    .title("Marker in Sydney"));

            CameraPosition newPos = new CameraPosition.Builder()
                    .target(new LatLng(lastKnownLocation.getLatitude(),
                            lastKnownLocation.getLongitude()))
                    .zoom(14)
                    .tilt(45)
                    .bearing(200)
                    .build();

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(newPos), new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {
                    mMap.getUiSettings().setScrollGesturesEnabled(true);
                }

                @Override
                public void onCancel() {
                    mMap.getUiSettings().setAllGesturesEnabled(true);
                }
            });
        }
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

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            lastKnownLocation = location;

                            updateMapView();
                        }
                    }
                });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    /****************************************************************
     * LISTENING TO ALL THE SIGNAL INTO THIS FRAGMENT BY EVENT BUS
     * @UpdateMapPaddingBus: Update the map padding
     **/
}