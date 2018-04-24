package com.app.concertbud.concertbuddies.ViewHolders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.concertbud.concertbuddies.Abstracts.OnChatRoomClickListener;
import com.app.concertbud.concertbuddies.AppControllers.BasePreferenceManager;
import com.app.concertbud.concertbuddies.Networking.Responses.Chatroom;
import com.app.concertbud.concertbuddies.R;
import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hungnguyen on 3/3/18.
 */
public class ChatRoomViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.root_view)
    RelativeLayout mRootView;
    @BindView(R.id.match_name)
    TextView mRoomName;
    @BindView(R.id.timestamp)
    TextView mTimeStamp;
    @BindView(R.id.last_message)
    TextView mLastMessage;

    private Unbinder unbinder;

    public ChatRoomViewHolder(View itemView) {
        super(itemView);
    }

    public void init(final Chatroom chatroom, final int position, final OnChatRoomClickListener listener) {
        unbinder = ButterKnife.bind(this, itemView);

        // TODO: mRoomImage

        // timestamp
        mTimeStamp.setText(DateUtils.getRelativeTimeSpanString(Long.parseLong(chatroom.getTimestamp())));

        // last read message
        mLastMessage.setText(chatroom.getLastMessage());

        // room name
        for (Map.Entry<String, Object> entry : chatroom.getUsers().entrySet()) {
            String key = entry.getKey();
            if (!key.equals(BasePreferenceManager.getDefault().getFcmToken())) {
                DatabaseReference mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(key).child("name");
                mDatabaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mRoomName.setText((String)dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            //mRoomName.setText(Profile.getCurrentProfile().getFirstName());
        }
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