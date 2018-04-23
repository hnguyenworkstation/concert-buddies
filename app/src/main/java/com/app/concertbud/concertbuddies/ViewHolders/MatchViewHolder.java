package com.app.concertbud.concertbuddies.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.concertbud.concertbuddies.Helpers.ImageLoader;
import com.app.concertbud.concertbuddies.Networking.Responses.MatchProfileResponse;
import com.app.concertbud.concertbuddies.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hungnguyen on 4/22/18.
 */
public class MatchViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.match_profile)
    ImageView mMatchProfile;
    @BindView(R.id.match_progress)
    ProgressBar mMatchProgress;
    @BindView(R.id.match_name)
    TextView mMatchName;

    private Unbinder unbinder;
    public MatchViewHolder(View itemView) {
        super(itemView);
    }

    public void init(MatchProfileResponse profileResponse) {
        unbinder = ButterKnife.bind(this, itemView);

        ImageLoader.loadSimpleCircleImage(mMatchProfile, profileResponse.getPicture().getDataEntity().getUrl(), mMatchProgress);
        mMatchName.setText(profileResponse.getFullName());
    }

    public void onRecycled() {
        if (unbinder != null)
            unbinder.unbind();
    }
}
