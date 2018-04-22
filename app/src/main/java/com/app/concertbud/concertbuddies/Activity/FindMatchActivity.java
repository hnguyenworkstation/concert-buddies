package com.app.concertbud.concertbuddies.Activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.concertbud.concertbuddies.Adapters.UserFindMatchAdapter;
import com.app.concertbud.concertbuddies.AppControllers.BaseActivity;
import com.app.concertbud.concertbuddies.AppControllers.BaseApplication;
import com.app.concertbud.concertbuddies.EventBuses.DeliverListMatchProfileBus;
import com.app.concertbud.concertbuddies.Helpers.TestUserData;
import com.app.concertbud.concertbuddies.Networking.Responses.BaseResponse;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.EventsEntity;
import com.app.concertbud.concertbuddies.Networking.Responses.MatchProfileResponse;
import com.app.concertbud.concertbuddies.Networking.Responses.UserResponse;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.Tasks.Configs.Jobs.FetchPotentialMatchesTask;
import com.app.concertbud.concertbuddies.Tasks.Configs.Jobs.PostSwipeTask;
import com.zc.swiple.SwipeFlingView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

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

    private UserFindMatchAdapter mAdapter;
    private EventsEntity eventsEntity;

    private Unbinder unbinder;
    private ArrayList<MatchProfileResponse> mUserList = new ArrayList<>();

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

    private void updateListView(List<MatchProfileResponse> list) {
        if (list == null || list.size() == 0) {
            return;
        }

        mUserList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.action_back)
    public void onActionBackClicked() {
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
        MatchProfileResponse response = mUserList.get((int) dataObject - 1);

        BaseApplication.getInstance().getJobManager()
                .addJobInBackground(new PostSwipeTask(response.getUserId(), false));
    }

    @Override
    public void onRightCardExit(View view, Object dataObject, boolean triggerByTouchMove) {
        MatchProfileResponse response = mUserList.get((int) dataObject - 1);

        BaseApplication.getInstance().getJobManager()
                .addJobInBackground(new PostSwipeTask(response.getUserId(), true));
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

    }

    @Override
    public void onScroll(View selectedView, float scrollProgressPercent) {

    }

    @Override
    public void onEndDragCard() {

    }

    @Subscribe(sticky = true)
    public void onEvent(DeliverListMatchProfileBus matchProfileBus) {
        if (matchProfileBus.getToClass().equals(FindMatchActivity.class.getSimpleName())) {
            updateListView(matchProfileBus.getMatchProfileResponseList());
        }
    }
}
