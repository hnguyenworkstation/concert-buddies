package com.app.concertbud.concertbuddies.Activity;

import android.app.Activity;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.concertbud.concertbuddies.Adapters.MessageAdapter;
import com.app.concertbud.concertbuddies.AppControllers.BaseActivity;
import com.app.concertbud.concertbuddies.AppControllers.BasePreferenceManager;
import com.app.concertbud.concertbuddies.Networking.Responses.Chatroom;
import com.app.concertbud.concertbuddies.Networking.Responses.Message;
import com.app.concertbud.concertbuddies.Networking.Responses.User;
import com.app.concertbud.concertbuddies.R;
//import com.app.concertbud.concertbuddies.ViewHolders.MessageViewHolder;
import com.facebook.Profile;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Calendar;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by huongnguyen on 3/22/18.
 */

public class ChatActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.send_btn)
    Button mSendButton;
    @BindView(R.id.edit_message)
    EditText mEditMsg;
    @BindView(R.id.chat_recycler)
    RecyclerView mChatRecycler;

    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.user_id)
    TextView mUserId;

    private DatabaseReference mDatabase;
    private Unbinder unbinder;
    private FirebaseRecyclerAdapter<Message,RecyclerView.ViewHolder> FBRA;

    private DatabaseReference mDatabaseMsgs;
    private DatabaseReference mDatabaseUsers;
    private DatabaseReference mDatabaseChatrooms;
    private FirebaseAuth mAuth;

    public static boolean isActivityRunning;
    private String chatRoomID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        isActivityRunning = true;

        mAuth = FirebaseAuth.getInstance();
        unbinder = ButterKnife.bind(this);

        Intent intent = getIntent();
        chatRoomID = intent.getStringExtra("chatRoomID");
        initChatRecycler();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Messages");
        mSendButton.setOnClickListener(this);

        // TODO: implement collapsing keyboard when click outside

        FirebaseDatabase.getInstance().getReference().child("Chatrooms").child(chatRoomID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Chatroom chatroom = dataSnapshot.getValue(Chatroom.class);

                        // room name
                        for (Map.Entry<String, Object> entry : chatroom.getUsers().entrySet()) {
                            String key = entry.getKey();
                            if (!key.equals(BasePreferenceManager.getDefault().getFcmToken())) {
                                DatabaseReference mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(key).child("name");
                                mDatabaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        mUserName.setText((String)dataSnapshot.getValue());
                                        mUserId.setText("Online");
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                            //mRoomName.setText(Profile.getCurrentProfile().getFirstName());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();
        FBRA.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        FBRA.stopListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        isActivityRunning = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_btn:
                mDatabaseMsgs = mDatabase.child(chatRoomID);
                mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(BasePreferenceManager.getDefault().getFcmToken());
                mDatabaseChatrooms = FirebaseDatabase.getInstance().getReference().child("Chatrooms");
                final String message = mEditMsg.getText().toString().trim();
                if (!TextUtils.isEmpty(message)) {
                    final DatabaseReference newPostMsg = mDatabaseMsgs.push();
                    final DatabaseReference newPostChatroom = mDatabaseChatrooms.child(chatRoomID);
                    mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
                            newPostMsg.child("timestamp").setValue(timestamp);
                            newPostMsg.child("content").setValue(message);
                            User sender = dataSnapshot.getValue(User.class);
                            newPostMsg.child("senderName").setValue(sender.getName());

                            /* Update chatroom lastMsg and timestamp */
                            newPostChatroom.child("lastMessage").setValue(message);
                            newPostChatroom.child("timestamp").setValue(timestamp);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    mEditMsg.setText("");
                }
                break;
        }
    }

    private void initChatRecycler() {
        final RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        ((LinearLayoutManager) mLayoutManager).setStackFromEnd(true);
        mChatRecycler.setHasFixedSize(true);
        mChatRecycler.setLayoutManager(mLayoutManager);
        /* show 50 most recent messages */
        Query query = FirebaseDatabase.getInstance().getReference().child("Messages").child(chatRoomID).limitToLast(50);
        FirebaseRecyclerOptions<Message> options =
                new FirebaseRecyclerOptions.Builder<Message>().setQuery(query, Message.class).build();
        FBRA = new MessageAdapter(options);
        FBRA.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                mLayoutManager.smoothScrollToPosition(mChatRecycler, null, FBRA.getItemCount());
            }
        });
        mChatRecycler.setAdapter(FBRA);
    }
}
