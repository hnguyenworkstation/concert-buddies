package com.app.concertbud.concertbuddies.Activity;

import android.annotation.SuppressLint;
import android.location.Location;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    @BindView(R.id.home_tab)
    FloatingActionButton mHomeTabBtn;
    @BindView(R.id.loc_map_tab)
    FloatingActionButton mLocMapTabBtn;
    @BindView(R.id.advance_tab)
    FloatingActionButton mAdvanceTab;

    @BindView(R.id.tab_title)
    TextView mTabTitle;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.image_btn)
    ImageView mProfileImg;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    /* Binding Switcher Layout */
    @BindView(R.id.switcher_layout)
    RelativeLayout mSwitcherLayout;
    @BindView(R.id.map_selector)
    LinearLayout mMapSelector;
    @BindView(R.id.map_text)
    TextView mMapText;
    @BindView(R.id.list_selector)
    LinearLayout mListSelector;
    @BindView(R.id.list_text)
    TextView mListText;

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
                        initOnClicks();
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

    private void initOnClicks() {
        mHomeTabBtn.setOnClickListener(this);
        mLocMapTabBtn.setOnClickListener(this);
        mAdvanceTab.setOnClickListener(this);

        mMapSelector.setOnClickListener(this);
        mListSelector.setOnClickListener(this);
    }

    private void showViewAt(int position) {
        if (position >= 0 && position <= 2) {
            mViewPager.setCurrentItem(position);
        }
    }


    /*
    * This method will make sure to keep track of which stage the LocateEventFragment are on
    * (either on Map stage or List stage). Then it will send a request signal (using Eventbus)
    * to change stage from MainActivity to LocateEventFragment to trigger the view switch.
    * */
    private void triggerSwitchView(int code) {
        currentSecondStage = code;
        showSwitcherView(true);
        EventBus.getDefault().post(new TriggerViewBus(code));
    }


    /*
    * showSwitcherView function will be triggered when user are currently viewing the LocateEventFragment
    * it allows user to switch between fragments between List View or Map View
    * */
    private void showSwitcherView(boolean shouldBeShown) {
        if (shouldBeShown) {
            mTabTitle.setVisibility(View.GONE);
            mSwitcherLayout.setVisibility(View.VISIBLE);

            if (currentSecondStage == 0) {
                mMapSelector.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                mMapText.setTextColor(getResources().getColor(R.color.white));

                mListSelector.setBackground(null);
                mListText.setTextColor(getResources().getColor(R.color.text_light_color));
            } else {
                mListSelector.setBackground(getResources().getDrawable(R.drawable.bg_button_selected));
                mListText.setTextColor(getResources().getColor(R.color.white));

                mMapSelector.setBackground(null);
                mMapText.setTextColor(getResources().getColor(R.color.text_light_color));
            }
        } else {
            mTabTitle.setVisibility(View.VISIBLE);
            mSwitcherLayout.setVisibility(View.GONE);
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
                switch (position) {
                    case 0:
                        mTabTitle.setText("Following");
                        showSwitcherView(false);
                        break;
                    case 1:
                        mTabTitle.setText("");
                        showSwitcherView(true);
                        break;
                    case 2:
                        mTabTitle.setText("Matches");
                        showSwitcherView(false);
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
            case R.id.map_selector:
                if (!isMapAnimating)
                    triggerSwitchView(MAP_VIEW_CODE);
                break;
            case R.id.list_selector:
                if (!isMapAnimating)
                    triggerSwitchView(LIST_VIEW_CODE);
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

    /* Subscribe last known location event */
    @Subscribe
    public void onEvent(DeliverLocationBus bus) {
        lastKnowLocation = bus.getLocation();
        Log.e(TAG, "Last known location: " + lastKnowLocation.toString());
    }
}

