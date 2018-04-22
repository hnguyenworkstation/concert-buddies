package com.app.concertbud.concertbuddies.Activity;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.concertbud.concertbuddies.Adapters.CommonViewPagerAdapter;
import com.app.concertbud.concertbuddies.AppControllers.BaseActivity;
import com.app.concertbud.concertbuddies.EventBuses.DeliverLocationBus;
import com.app.concertbud.concertbuddies.EventBuses.IsOnAnimationBus;
import com.app.concertbud.concertbuddies.EventBuses.TriggerViewBus;
import com.app.concertbud.concertbuddies.Helpers.ImageLoader;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.ViewFragments.LocateEventFragment;
import com.app.concertbud.concertbuddies.ViewFragments.MatchesFragment;
import com.app.concertbud.concertbuddies.ViewFragments.SubscribedEventsFragment;
import com.facebook.Profile;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.messaging.FirebaseMessaging;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.SettingService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomTabbar;

    @BindView(R.id.tab_view)
    RelativeLayout mTabView;
    @BindView(R.id.tab_title)
    TextView mTabTitle;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.image_btn)
    ImageView mProfileImg;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    public static final int MAP_VIEW_CODE = 0;
    public static final int LIST_VIEW_CODE = 1;

    private Profile mUserProfile;

    private Unbinder unbinder;
    private CommonViewPagerAdapter mMainViewPagerAdapter;

    private LocateEventFragment locateEventFragment;
    private MatchesFragment matchesFragment;
    private SubscribedEventsFragment subscribedEventsFragment;
    private SettingService settingService;
    private Location lastKnowLocation;

    private int currentSecondStage = MAP_VIEW_CODE;

    private boolean isMapAnimating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        getPermission();

        // <<<
        // Subscribe to Notifications
        FirebaseMessaging.getInstance().subscribeToTopic("pushNotifications");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }
    }

    private void getPermission() {
        AndPermission.with(this)
                .permission(Permission.Group.LOCATION)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        initContents();

                        initView();
                        initViewPager();
                    }
                }).onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(getBaseContext(), permissions)) {

                        } else {
                            Toast.makeText(MainActivity.this, "need permission", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .start();
    }

    private void initContents() {
        locateEventFragment = LocateEventFragment.newInstance(mViewPager.getCurrentItem());
        matchesFragment = MatchesFragment.newInstance();
        subscribedEventsFragment = SubscribedEventsFragment.newInstance();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        ImageLoader.loadSimpleCircleImage(mProfileImg,
                Profile.getCurrentProfile().getProfilePictureUri(248, 248).toString(), mProgressBar);

        mTabTitle.setText("Following");
    }

    private void showViewAt(int position) {
        switch (position) {
            case 0:
            case 2:
                mTabView.setVisibility(View.VISIBLE);
                break;
            case 1:
                mTabView.setVisibility(View.GONE);
                break;
            default:
                break;

        }

        if (position >= 0 && position <= 2) {
            mViewPager.setCurrentItem(position);
        }
    }

    private void initViewPager() {
        mMainViewPagerAdapter = new CommonViewPagerAdapter(getSupportFragmentManager());

        mMainViewPagerAdapter.addFragment(subscribedEventsFragment, "");
        mMainViewPagerAdapter.addFragment(locateEventFragment, "");
        mMainViewPagerAdapter.addFragment(matchesFragment, "");


        /** the ViewPager requires a minimum of 1 as OffscreenPageLimit */
        int limit = (mMainViewPagerAdapter.getCount() > 1 ? mMainViewPagerAdapter.getCount() - 1 : 1);

        mViewPager.setAdapter(mMainViewPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onPageSelected(int position){
                mBottomTabbar.getMenu().getItem(position).setChecked(true);

                switch (position) {
                    case 0:
                        mTabTitle.setText("Following");
                        mTabView.setVisibility(View.VISIBLE);
                        SubscribedEventsFragment.self.loadEvents();
                        break;
                    case 1:
                        mTabTitle.setText("");
                        mTabView.setVisibility(View.GONE);
                        break;
                    case 2:
                        mTabTitle.setText("Matches");
                        mTabView.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setOffscreenPageLimit(limit);
        /*
        * Setting up the bottom tabbar with viewpager
        * */
        mBottomTabbar.setOnNavigationItemSelectedListener(new BottomNavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_tab:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.explore_tab:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.matches_tab:
                        mViewPager.setCurrentItem(2);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_tab:
                showViewAt(0);
                break;
            case R.id.loc_map_tab:
                showViewAt(1);
                break;
            case R.id.advance_tab:
                showViewAt(2);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        Log.e(TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(IsOnAnimationBus bus) {
        Log.e(TAG, "animation");
        isMapAnimating = bus.isOnAnimation();
    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 1 && getSupportFragmentManager().getBackStackEntryCount() == 1) {
            EventBus.getDefault().post(new TriggerViewBus(MAP_VIEW_CODE));
        } else {
            super.onBackPressed();
        }
    }

    /* Subscribe last known location event */
    @Subscribe
    public void onEvent(DeliverLocationBus bus) {
        lastKnowLocation = bus.getLocation();
        Log.e(TAG, "Last known location: " + lastKnowLocation.toString());
    }
}

