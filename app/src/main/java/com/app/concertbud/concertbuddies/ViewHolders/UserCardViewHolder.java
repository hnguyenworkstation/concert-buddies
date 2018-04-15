package com.app.concertbud.concertbuddies.ViewHolders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.concertbud.concertbuddies.Helpers.ImageLoader;
import com.app.concertbud.concertbuddies.Networking.Responses.UserResponse;
import com.app.concertbud.concertbuddies.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hungnguyen on 4/6/18.
 */

public class UserCardViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.root_view)
    FrameLayout cardLayout;
    @BindView(R.id.item_img)
    ImageView img;
    @BindView(R.id.item_name)
    TextView nameView;
    @BindView(R.id.item_address)
    TextView addressView;
    @BindView(R.id.item_friend_count)
    TextView mFriendCountTv;
    @BindView(R.id.item_interest_count)
    TextView mInterestCountTv;
    @BindView(R.id.item_bottom_layout)
    ViewGroup mBottomLayout;

    private Unbinder unbinder;

    public UserCardViewHolder(View itemView) {
        super(itemView);
    }

    public void initView(UserResponse response) {
        unbinder = ButterKnife.bind(this, itemView);
        ImageLoader.loadSimpleImage(img, response.getUrl(), null);

        nameView.setText(response.getWho());
        addressView.setText(response.getDesc());
    }

    public void onViewRecycled() {
        unbinder.unbind();
    }
}
