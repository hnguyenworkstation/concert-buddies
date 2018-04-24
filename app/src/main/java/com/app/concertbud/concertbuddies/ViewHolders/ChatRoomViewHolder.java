package com.app.concertbud.concertbuddies.ViewHolders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.concertbud.concertbuddies.Abstracts.OnChatRoomClickListener;
import com.app.concertbud.concertbuddies.AppControllers.BasePreferenceManager;
import com.app.concertbud.concertbuddies.CustomUI.AdjustableImageView;
import com.app.concertbud.concertbuddies.Helpers.ImageLoader;
import com.app.concertbud.concertbuddies.Networking.NetContext;
import com.app.concertbud.concertbuddies.Networking.Responses.Chatroom;
import com.app.concertbud.concertbuddies.Networking.Responses.HerokuUser;
import com.app.concertbud.concertbuddies.Networking.Services.BackendServices;
import com.app.concertbud.concertbuddies.R;
import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @BindView(R.id.logo_image)
    AdjustableImageView mLogoImage;
    @BindView(R.id.profile_progress)
    ProgressBar mProgressBar;

    private Unbinder unbinder;

    public ChatRoomViewHolder(View itemView) {
        super(itemView);
    }

    public void init(final Chatroom chatroom, final int position, final OnChatRoomClickListener listener, final Resources resources) {
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
                        mRoomName.setText(dataSnapshot.getValue(String.class)); // <<<
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                DatabaseReference mDatabasePhoto = FirebaseDatabase.getInstance().getReference().child("Users").child(key).child("fb_id");
                mDatabasePhoto.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        BackendServices backendServices = NetContext.instance.create(BackendServices.class);
                        backendServices.getUser(dataSnapshot.getValue(String.class)).enqueue(new Callback<HerokuUser>() {
                            @Override
                            public void onResponse(Call<HerokuUser> call, Response<HerokuUser> response) {
                                if (response.code() == 200) {
                                    // update Image
                                    // TODO: UPDATE IMAGE IN HERE
//                                    ImageLoader.loadCircleAdjustImageFromURI(mLogoImage,
//                                            Uri.parse(response.body().getImage().getUrl()),
//                                            mProgressBar);
                                    //mLogoImage.setImageBitmap(ImageLoader.getBitmapFromURL(response.body().getImage().getUrl()));
                                    final String url = response.body().getImage().getImage().getUrl();
                                    Picasso.get().load(url).fit().centerCrop().into(mLogoImage, new com.squareup.picasso.Callback() {
                                        @Override
                                        public void onSuccess() {
                                            Log.e("LOADING IMAGE", "profile pic link: " + url);
                                            mProgressBar.setVisibility(View.GONE);
                                            Bitmap imgBitmap = ((BitmapDrawable) mLogoImage.getDrawable()).getBitmap();
                                            RoundedBitmapDrawable imgDrawable = RoundedBitmapDrawableFactory.create(resources, imgBitmap);
                                            imgDrawable.setCircular(true);
                                            imgDrawable.setCornerRadius(Math.max(imgBitmap.getWidth(), imgBitmap.getHeight()) / 2.0f);
                                            mLogoImage.setImageDrawable(imgDrawable);
                                        }

                                        @Override
                                        public void onError(Exception e) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onFailure(Call<HerokuUser> call, Throwable t) {

                            }
                        });
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