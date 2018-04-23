package com.app.concertbud.concertbuddies.Activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.concertbud.concertbuddies.Adapters.UserFindMatchAdapter;
import com.app.concertbud.concertbuddies.AppControllers.BaseActivity;
import com.app.concertbud.concertbuddies.AppControllers.BaseApplication;
import com.app.concertbud.concertbuddies.EventBuses.DeliverListMatchProfileBus;
import com.app.concertbud.concertbuddies.EventBuses.DeliverMatchResponseBus;
import com.app.concertbud.concertbuddies.Helpers.TestUserData;
import com.app.concertbud.concertbuddies.Networking.Responses.BaseResponse;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.EventsEntity;
import com.app.concertbud.concertbuddies.Networking.Responses.MatchProfileResponse;
import com.app.concertbud.concertbuddies.Networking.Responses.MatchResponse;
import com.app.concertbud.concertbuddies.Networking.Responses.UserResponse;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.Tasks.Configs.Jobs.FetchPotentialMatchesTask;
import com.app.concertbud.concertbuddies.Tasks.Configs.Jobs.PostSwipeTask;
import com.app.concertbud.concertbuddies.ViewFragments.MatchesFragment;
import com.zc.swiple.SwipeFlingView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

public class FindMatchActivity extends BaseActivity implements SwipeFlingView.OnSwipeFlingListener{
    private final static String TAG = FindMatchActivity.class.getSimpleName();
    private final static boolean DEBUG = true;

    @BindView(R.id.frame)
    SwipeFlingView mSwipeFlingView;
    @BindView(R.id.event_name)
    TextView mEventName;
    @BindView(R.id.event_id)
    TextView mEventId;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.empty_message)
    TextView mEmptyMessage;

    private UserFindMatchAdapter mAdapter;
    private EventsEntity eventsEntity;

    private Unbinder unbinder;
    private ArrayList<MatchProfileResponse> mUserList = new ArrayList<>();

    private boolean canLoadMore = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_match);

        unbinder = ButterKnife.bind(this);

        eventsEntity = (EventsEntity) getIntent().getSerializableExtra("EventsEntity");

        initView();

        // Send request to udpate layout
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BaseApplication.getInstance().getJobManager()
                        .addJobInBackground(new FetchPotentialMatchesTask(eventsEntity.getId()));
            }
        }, 1000);
    }

    private void initView() {
        mEventName.setText(eventsEntity.getName());
        mEventId.setText(eventsEntity.getId());

        mAdapter = new UserFindMatchAdapter(this, mUserList);
        mSwipeFlingView.setAdapter(mAdapter);
        mSwipeFlingView.setOnSwipeFlingListener(this);
    }

    // this is kinda ugly but idc
    Map<String, String> idNameMap = new HashMap<>();
    private void updateListView(List<MatchProfileResponse> list) {
        if (list == null || list.size() == 0) {
            canLoadMore = false;

            if (mSwipeFlingView.hasNoEnoughCardSwipe()) {
                mEmptyMessage.setVisibility(View.VISIBLE);
            }
            return;
        }

        for (MatchProfileResponse person : list) {
            idNameMap.put(person.getUserId(), person.getFullName());
        }
        mUserList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.action_back)
    public void onActionBackClicked() {
        mUserList.clear();
        mAdapter.notifyDataSetChanged();
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        } else {
            Log.e(TAG, "EventBus is registered");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if( EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }else{
            Log.e(TAG, "EventBus is not registered");
        }
    }

    @Override
    public void onStartDragCard() {

    }

    @Override
    public boolean canLeftCardExit() {
        return true;
    }

    @Override
    public boolean canRightCardExit() {
        return true;
    }

    @Override
    public void onPreCardExit() {

    }

    @Override
    public void onLeftCardExit(View view, Object dataObject, boolean triggerByTouchMove) {
        BaseApplication.getInstance().getJobManager()
                .addJobInBackground(new PostSwipeTask(eventsEntity.getId(), false));
    }

    @Override
    public void onRightCardExit(View view, Object dataObject, boolean triggerByTouchMove) {
        BaseApplication.getInstance().getJobManager()
                .addJobInBackground(new PostSwipeTask(eventsEntity.getId(), true));
    }

    @OnClick(R.id.swipe_right_btn)
    public void onSwipeRightBtn() {
        if (!mSwipeFlingView.hasNoEnoughCardSwipe()) {
            mSwipeFlingView.selectRight();
        }
    }

    @OnClick(R.id.swipe_left_btn)
    public void onSwipeLeftBtn() {
        if (!mSwipeFlingView.hasNoEnoughCardSwipe()) {
            mSwipeFlingView.selectLeft();
        }
    }

    @Override
    public void onSuperLike(View view, Object dataObject, boolean triggerByTouchMove) {

    }

    @Override
    public void onTopCardViewFinish() {

    }

    @Override
    public void onAdapterAboutToEmpty(int itemsInAdapter) {
        BaseApplication.getInstance().getJobManager()
                .addJobInBackground(new FetchPotentialMatchesTask(eventsEntity.getId()));
    }

    @Override
    public void onAdapterEmpty() {
        mProgressBar.setVisibility(View.GONE);
        mEmptyMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onScroll(View selectedView, float scrollProgressPercent) {

    }

    @Override
    public void onEndDragCard() {

    }

    @Subscribe(sticky = true)
    public void onEvent(final DeliverListMatchProfileBus matchProfileBus) {
        if (matchProfileBus.getToClass().equals(FindMatchActivity.class.getSimpleName())
                && matchProfileBus.getType() == DeliverListMatchProfileBus.Type.POTENTIAL ) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (matchProfileBus.getEventId().equals(eventsEntity.getId())) {
                        mProgressBar.setVisibility(View.GONE);
                        updateListView(matchProfileBus.getMatchProfileResponseList());

                        if (matchProfileBus.getMatchProfileResponseList() == null ||
                                matchProfileBus.getMatchProfileResponseList().size() == 0) {
                            canLoadMore = false;
                        }
                    }
                }
            });

            EventBus.getDefault().removeStickyEvent(matchProfileBus);
        }
    }

    @Subscribe(sticky = true)
    public void likeResponse(final DeliverMatchResponseBus matchResponseBus) {
        if (matchResponseBus.getToClass().equals(FindMatchActivity.class.getSimpleName())) {
            Log.d("chris", "its lit " + matchResponseBus.getMatchResponse().getMatch());
            MatchResponse mr = matchResponseBus.getMatchResponse();
            if (mr.getMatch().equals("1")) {
                if (idNameMap.containsKey(mr.getLikedId())) {
                    MatchesFragment.self.initNewChatroomFirebase(
                            mr.getLikedId(),
                            mr.getLikedFirebaseToken(),
                            idNameMap.get(mr.getLikedId()));
                } else {
                    Log.d("chris",
                            "this should never happen, we couldnt find the name??");
                    MatchesFragment.self.initNewChatroomFirebase(
                            idNameMap.get("SampleUser"),
                            mr.getLikedFirebaseToken(),
                            mr.getLikedId());
                }
            }
        }
    }


}
