package com.app.concertbud.concertbuddies.Activity;

import android.arch.lifecycle.LifecycleRegistry;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.app.concertbud.concertbuddies.Adapters.MessageAdapter;
import com.app.concertbud.concertbuddies.AppControllers.BaseActivity;
import com.app.concertbud.concertbuddies.Networking.Responses.Message;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.ViewHolders.MessageViewHolder;
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

import java.util.Calendar;

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

    private DatabaseReference mDatabase;
    private Unbinder unbinder;
    private FirebaseRecyclerAdapter<Message,MessageViewHolder> FBRA;

    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseUsers;
    private FirebaseAuth mAuth;

    private String chatRoomID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        mAuth = FirebaseAuth.getInstance();
        unbinder = ButterKnife.bind(this);

        Intent intent = getIntent();
        chatRoomID = intent.getStringExtra("chatRoomID");
        initChatRecycler();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Messages");

        mSendButton.setOnClickListener(this);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_btn:
                mCurrentUser = mAuth.getCurrentUser();
                mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Messages").child(chatRoomID);
                final String message = mEditMsg.getText().toString().trim();
                if (!TextUtils.isEmpty(message)) {
                    final DatabaseReference newPost = mDatabaseUsers.push();
                    mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            newPost.child("timestamp").setValue(String.valueOf(Calendar.getInstance().getTimeInMillis()));
                            newPost.child("content").setValue(message);
                            newPost.child("senderName").setValue(Profile.getCurrentProfile().getName());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
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
        Query query = FirebaseDatabase.getInstance().getReference().child("Messages").limitToLast(50);
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
