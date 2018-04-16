package com.app.concertbud.concertbuddies.ViewFragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.app.concertbud.concertbuddies.Abstracts.OnChatRoomClickListener;
import com.app.concertbud.concertbuddies.Activity.ChatActivity;
import com.app.concertbud.concertbuddies.Adapters.ChatRoomAdapter;
import com.app.concertbud.concertbuddies.Helpers.AppUtils;
import com.app.concertbud.concertbuddies.Networking.Responses.Chatroom;
import com.app.concertbud.concertbuddies.Networking.Responses.User;
import com.app.concertbud.concertbuddies.R;
import com.facebook.Profile;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchesFragment extends Fragment implements OnChatRoomClickListener{
    @BindView(R.id.chat_recycler)
    RecyclerView mRoomsRecycler;
    // <<<
    @BindView(R.id.newchat)
    Button newChatBtn;
    private ArrayList<String> chatrooms;
    private Set<String> chatroomsSoFar;
    private OnChatRoomClickListener listener = this;

    private ChatRoomAdapter chatRoomAdapter;
    private DatabaseReference chatRoomsRef;
    private Unbinder unbinder;

    public MatchesFragment() {
        // Required empty public constructor
    }

    public static MatchesFragment newInstance() {
        MatchesFragment fragment = new MatchesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_matches, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        // <<<
        // TODO: remove this when swipe tinder is implemented
        newChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initNewChatroomFirebase();
            }
        });

        initChatRoomsRecycler();
    }

    private void initNewChatroomFirebase() {
        final String facebook_id = Profile.getCurrentProfile().getId();
        // TODO: replace 1234 with actual user
        /* Chatrooms database ref */
        chatRoomsRef = FirebaseDatabase.getInstance().getReference().child("Chatrooms");
        final String chatRoomId = chatRoomsRef.push().getKey();
        Chatroom chatroom = new Chatroom("", String.valueOf(Calendar.getInstance().getTimeInMillis()));
        Map<String, Object> postValues = chatroom.toMap();
        chatRoomsRef.child(chatRoomId).updateChildren(postValues);
        chatRoomsRef.child(chatRoomId).child("users").child(facebook_id).setValue(true);
        chatRoomsRef.child(chatRoomId).child("users").child("1234").setValue(true);

        /* Users database ref */
        final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        // determine if user already exists
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child(facebook_id).exists()) {
                    User user = new User(Profile.getCurrentProfile().getName(), facebook_id);
                    Map<String, Object> postValues = user.toMap();
                    usersRef.child(facebook_id).updateChildren(postValues);
                }
                if (!dataSnapshot.child("1234").exists()) {
                    User user = new User("testUser", "1234");
                    Map<String, Object> postValues = user.toMap();
                    usersRef.child("1234").updateChildren(postValues);
                }
                // update both users with new chatroom
                usersRef.child(facebook_id).child("chatrooms").child(chatRoomId).setValue(true);
                usersRef.child("1234").child("chatrooms").child(chatRoomId).setValue(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //AppUtils.startNewActivity(getContext(), getActivity(), ChatActivity.class);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getContext().startActivity(new Intent(getActivity(), ChatActivity.class).putExtra("chatRoomID", chatRoomId));
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 0);
    }

    private void initChatRoomsRecycler() {
        final RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mRoomsRecycler.setLayoutManager(mLayoutManager);
        mRoomsRecycler.setItemAnimator(new DefaultItemAnimator());
        mRoomsRecycler.setNestedScrollingEnabled(false);
        mRoomsRecycler.setHasFixedSize(false);
        getChatRooms();
    }

    private void getChatRooms() {
        chatrooms = new ArrayList<>();
        chatroomsSoFar = new HashSet<>();
        chatRoomAdapter = new ChatRoomAdapter(getContext(), chatrooms, listener);
        mRoomsRecycler.setAdapter(chatRoomAdapter);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mRoomsRecycler);
        FirebaseDatabase.getInstance().getReference().child("Users").child(Profile.getCurrentProfile().getId()).child("chatrooms")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot entry: dataSnapshot.getChildren()) {
                            if (!chatroomsSoFar.contains(entry.getKey()))
                                chatrooms.add(entry.getKey());
                        }
                        chatRoomAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onChatRoomClicked(final int position) {
        Toast.makeText(getContext(), "clicked at: " + position, Toast.LENGTH_SHORT).show();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getContext().startActivity(new Intent(getActivity(), ChatActivity.class).putExtra("chatRoomID", chatrooms.get(position)));
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 0);
    }

    @Override
    public void onChatRoomLongClicked(int position) {

    }
}
