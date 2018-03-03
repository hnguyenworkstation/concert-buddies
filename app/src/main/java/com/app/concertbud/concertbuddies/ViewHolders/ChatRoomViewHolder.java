package com.app.concertbud.concertbuddies.ViewHolders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.concertbud.concertbuddies.Abstracts.OnChatRoomClickListener;
import com.app.concertbud.concertbuddies.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hungnguyen on 3/3/18.
 */
public class ChatRoomViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.root_view)
    CardView mRootView;
    @BindView(R.id.logo_image)
    ImageView mChatRoomProfile;
    @BindView(R.id.room_status)
    ImageView mRoomStatusIcon;
    @BindView(R.id.profile_progress)
    ProgressBar mProfileProgress;
    @BindView(R.id.room_name)
    TextView mRoomName;
    @BindView(R.id.timestamp)
    TextView mTimeStamp;
    @BindView(R.id.last_message)
    TextView mLastMessage;

    private Unbinder unbinder;

    public ChatRoomViewHolder(View itemView) {
        super(itemView);
        unbinder = ButterKnife.bind(this, itemView);
    }

    public void init(final int position, final OnChatRoomClickListener listener) {
        unbinder = ButterKnife.bind(this, itemView);

        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onChatRoomClicked(position);
            }
        });

        mRootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onChatRoomLongClicked(position);
                return false;
            }
        });
    }

    public void onRecycled() {
        unbinder.unbind();
    }
}