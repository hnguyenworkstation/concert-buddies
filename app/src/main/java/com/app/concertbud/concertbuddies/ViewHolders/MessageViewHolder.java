package com.app.concertbud.concertbuddies.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.app.concertbud.concertbuddies.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by huongnguyen on 3/25/18.
 */

public class MessageViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.message)
    TextView message;
    @BindView(R.id.user_name)
    TextView username;

    private Unbinder unbinder;

    public MessageViewHolder(View itemView) {
        super(itemView);
        unbinder = ButterKnife.bind(this, itemView);
    }

    public void setContent(String content) {
        message.setText(content);
    }

    public void setUsername(String name) {
        username.setText(name);
    }

    public void onRecycled() {
        unbinder.unbind();
    }
}
