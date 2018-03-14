package com.app.concertbud.concertbuddies.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.app.concertbud.concertbuddies.Helpers.ImageLoader;
import com.app.concertbud.concertbuddies.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hungnguyen on 3/14/18.
 */

public class TinderCardViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_avatar)
    ImageView mAvatar;

    private Unbinder unbinder;

    public TinderCardViewHolder(View itemView) {
        super(itemView);
    }

    public void init() {
        unbinder = ButterKnife.bind(this, itemView);

        ImageLoader.loadSimpleImage(mAvatar, "https://www.codeproject.com/KB/GDI-plus/ImageProcessing2/flip.jpg", null);
    }

    public void onRecycled() {
        unbinder.unbind();
    }
}
