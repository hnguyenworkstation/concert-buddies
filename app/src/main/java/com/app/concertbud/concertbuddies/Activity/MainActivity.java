package com.app.concertbud.concertbuddies.Activity;

import android.annotation.SuppressLint;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.concertbud.concertbuddies.Adapters.CommonViewPagerAdapter;
import com.app.concertbud.concertbuddies.AppControllers.BaseActivity;
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

    private Profile mUserProfile;

    private Unbinder unbinder;
    private CommonViewPagerAdapter mMainViewPagerAdapter;

    private LocateEventFragment locateEventFragment;
    private MatchesFragment matchesFragment;
    private SubscribedEventsFragment subscribedEventsFragment;
    private SettingService settingService;

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
        locateEventFragment = LocateEventFragment.newInstance();
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
    }

    private void showViewAt(int position) {
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
                switch (position) {
                    case 0:
                        mTabTitle.setText("Following");
                        break;
                    case 1:
                        mTabTitle.setText("");
                        break;
                    case 2:
                        mTabTitle.setText("Matches");
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
            default:
                break;
        }
    }
}

