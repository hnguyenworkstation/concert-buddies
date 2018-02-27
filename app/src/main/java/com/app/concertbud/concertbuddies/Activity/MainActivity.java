package com.app.concertbud.concertbuddies.Activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.app.concertbud.concertbuddies.Adapters.CommonViewPagerAdapter;
import com.app.concertbud.concertbuddies.AppControllers.BaseActivity;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.ViewFragments.LocateEventFragment;
import com.app.concertbud.concertbuddies.ViewFragments.MatchesFragment;
import com.app.concertbud.concertbuddies.ViewFragments.SubscribedEventsFragment;

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

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private Unbinder unbinder;
    private CommonViewPagerAdapter mMainViewPagerAdapter;

    private LocateEventFragment locateEventFragment;
    private MatchesFragment matchesFragment;
    private SubscribedEventsFragment subscribedEventsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        initContents();

        initView();
        initViewPager();
        initOnClicks();
    }

    private void initContents() {
        locateEventFragment = LocateEventFragment.newInstance();
        matchesFragment = MatchesFragment.newInstance();
        subscribedEventsFragment = SubscribedEventsFragment.newInstance();
    }

    private void initView() {

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

            @Override
            public void onPageSelected(int position){

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

