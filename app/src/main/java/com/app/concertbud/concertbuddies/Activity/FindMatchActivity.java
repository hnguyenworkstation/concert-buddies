package com.app.concertbud.concertbuddies.Activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.concertbud.concertbuddies.Adapters.UserFindMatchAdapter;
import com.app.concertbud.concertbuddies.AppControllers.BaseActivity;
import com.app.concertbud.concertbuddies.Helpers.TestUserData;
import com.app.concertbud.concertbuddies.Networking.Responses.BaseResponse;
import com.app.concertbud.concertbuddies.Networking.Responses.UserResponse;
import com.app.concertbud.concertbuddies.R;
import com.zc.swiple.SwipeFlingView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

public class FindMatchActivity extends BaseActivity implements SwipeFlingView.OnSwipeFlingListener{
    private final static String TAG = FindMatchActivity.class.getSimpleName();
    private final static boolean DEBUG = true;

    @BindView(R.id.frame)
    SwipeFlingView mSwipeFlingView;

    private UserFindMatchAdapter mAdapter;

    private Unbinder unbinder;
    private ArrayList<UserResponse> mUserList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_match);

        unbinder = ButterKnife.bind(this);

        initView();
        addTestData();
    }

    private void initView() {
        mAdapter = new UserFindMatchAdapter(this, mUserList);
        mSwipeFlingView.setAdapter(mAdapter);
        mSwipeFlingView.setOnSwipeFlingListener(this);
    }

    private void updateListView(ArrayList<UserResponse> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        mUserList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    private void addTestData() {
        updateListView(TestUserData.getFakeData(getBaseContext()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
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

    }

    @Override
    public void onRightCardExit(View view, Object dataObject, boolean triggerByTouchMove) {

    }

    @Override
    public void onSuperLike(View view, Object dataObject, boolean triggerByTouchMove) {

    }

    @Override
    public void onTopCardViewFinish() {

    }

    @Override
    public void onAdapterAboutToEmpty(int itemsInAdapter) {
        addTestData();
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
}
