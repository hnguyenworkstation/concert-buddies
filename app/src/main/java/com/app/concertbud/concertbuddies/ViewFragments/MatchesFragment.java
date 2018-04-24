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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.app.concertbud.concertbuddies.Abstracts.OnChatRoomClickListener;
import com.app.concertbud.concertbuddies.Abstracts.OnMatchClickListener;
import com.app.concertbud.concertbuddies.Activity.ChatActivity;
import com.app.concertbud.concertbuddies.Activity.FindMatchActivity;
import com.app.concertbud.concertbuddies.Adapters.ChatRoomAdapter;
import com.app.concertbud.concertbuddies.Adapters.MatchesAdapter;
import com.app.concertbud.concertbuddies.AppControllers.BaseApplication;
import com.app.concertbud.concertbuddies.AppControllers.BasePreferenceManager;
import com.app.concertbud.concertbuddies.EventBuses.DeliverListMatchProfileBus;
import com.app.concertbud.concertbuddies.Helpers.AppUtils;
import com.app.concertbud.concertbuddies.Helpers.DataUtils;
import com.app.concertbud.concertbuddies.Networking.NetContext;
import com.app.concertbud.concertbuddies.Networking.Responses.Chatroom;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.EventsEntity;
import com.app.concertbud.concertbuddies.Networking.Responses.HerokuUser;
import com.app.concertbud.concertbuddies.Networking.Responses.MatchProfileResponse;
import com.app.concertbud.concertbuddies.Networking.Responses.MatchResponse;
import com.app.concertbud.concertbuddies.Networking.Responses.User;
import com.app.concertbud.concertbuddies.Networking.Services.BackendServices;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.Tasks.Configs.Jobs.FetchMatchesTask;
import com.facebook.Profile;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchesFragment extends Fragment implements OnChatRoomClickListener, OnMatchClickListener{
    @BindView(R.id.chat_recycler)
    RecyclerView mRoomsRecycler;
    @BindView(R.id.newchat)
    Button newChatBtn;
    @BindView(R.id.matches_recycler)
    RecyclerView mMatchesRecyclerView;

    private ArrayList<String> chatrooms;
    private List<MatchProfileResponse> matchResponseList;

    private Set<String> chatroomsSoFar;
    private OnChatRoomClickListener listener = this;

    private ChatRoomAdapter chatRoomAdapter;
    private MatchesAdapter matchesAdapter;

    private DatabaseReference chatRoomsRef;
    private Unbinder unbinder;

    public static MatchesFragment self;

    public MatchesFragment() {
        self = this;
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

        matchResponseList = new ArrayList<>();
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

        newChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initNewChatroomFirebase("1234", "1234", "testUser");
            }
        });

        BaseApplication.getInstance().getJobManager().addJobInBackground(new FetchMatchesTask());

        initChatRoomsRecycler();
        initMatchRecycler();
    }

    public void initNewChatroomFirebase(final String match_fb_id, final String match_fcm_token,
                                         final String match_name) {
        final String facebook_id = Profile.getCurrentProfile().getId();
        final String fcm_token = BasePreferenceManager.getDefault().getFcmToken();
        // TODO: replace 1234 with actual user
        BackendServices backendServices = NetContext.instance.create(BackendServices.class);
        backendServices.getUser(facebook_id).enqueue(new Callback<HerokuUser>() {
            @Override
            public void onResponse(Call<HerokuUser> call, Response<HerokuUser> response) {
                if (response.code() == 200) {
                    //String match_fcm_token = response.body().getFirebase_token();
                    // TODO: facebook_id is wrong, needs to be match's facebook id
                    createFirebaseChatroom(facebook_id, fcm_token, match_fcm_token,
                            match_fb_id, match_name);
                }
            }

            @Override
            public void onFailure(Call<HerokuUser> call, Throwable t) {

            }
        });
//        /* Chatrooms database ref */
//        chatRoomsRef = FirebaseDatabase.getInstance().getReference().child("Chatrooms");
//        final String chatRoomId = chatRoomsRef.push().getKey();
//        Chatroom chatroom = new Chatroom("", String.valueOf(Calendar.getInstance().getTimeInMillis()), facebook_id);
//        Map<String, Object> postValues = chatroom.toMap();
//        chatRoomsRef.child(chatRoomId).updateChildren(postValues);
//        chatRoomsRef.child(chatRoomId).child("users").child(fcm_token).setValue(true);
//        chatRoomsRef.child(chatRoomId).child("users").child("1234").setValue(true);

//        /* Users database ref */
//        final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
//        // determine if user already exists
//        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (!dataSnapshot.child(facebook_id).exists()) {
//                    User user = new User(Profile.getCurrentProfile().getName(), facebook_id);
//                    Map<String, Object> postValues = user.toMap();
//                    usersRef.child(facebook_id).updateChildren(postValues);
//                }
//                if (!dataSnapshot.child("1234").exists()) {
//                    User user = new User("testUser", "1234");
//                    Map<String, Object> postValues = user.toMap();
//                    usersRef.child("1234").updateChildren(postValues);
//                }
//                // update both users with new chatroom
//                usersRef.child(facebook_id).child("chatrooms").child(chatRoomId).setValue(true);
//                usersRef.child("1234").child("chatrooms").child(chatRoomId).setValue(true);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        //AppUtils.startNewActivity(getContext(), getActivity(), ChatActivity.class);
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                getContext().startActivity(new Intent(getActivity(), ChatActivity.class).putExtra("chatRoomID", chatRoomId));
//            }
//        };
//        Handler handler = new Handler();
//        handler.postDelayed(runnable, 0);
    }

    private void createFirebaseChatroom(final String facebook_id, final String fcm_token,
                                        final String match_fcm_token, final String match_fb_id,
                                        final String match_name) {
        Log.d("chris", "match name: " + match_name);
        /* Chatrooms database ref */
        chatRoomsRef = FirebaseDatabase.getInstance().getReference().child("Chatrooms");
        final String chatRoomId = chatRoomsRef.push().getKey();
        Chatroom chatroom = new Chatroom("", String.valueOf(Calendar.getInstance().getTimeInMillis()));
        Map<String, Object> postValues = chatroom.toMap();
        chatRoomsRef.child(chatRoomId).updateChildren(postValues);
        chatRoomsRef.child(chatRoomId).child("users").child(fcm_token).child("last_msg_seen").setValue(0);
        chatRoomsRef.child(chatRoomId).child("users").child(match_fcm_token).child("last_msg_seen").setValue(0);

        /* Users database ref */
        final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        // determine if user already exists
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child(fcm_token).exists()) {
                    User user = new User(Profile.getCurrentProfile().getName(), facebook_id);
                    Map<String, Object> postValues = user.toMap();
                    usersRef.child(fcm_token).updateChildren(postValues);
                }
                if (!dataSnapshot.child(match_fb_id).exists()) {
                    User user = new User(match_name, match_fb_id);
                    Map<String, Object> postValues = user.toMap();
                    usersRef.child(match_fb_id).updateChildren(postValues);
                }
                // update both users with new chatroom
                // <<<
                usersRef.child(fcm_token).child("chatrooms").child(chatRoomId).setValue(true);
                usersRef.child(match_fcm_token).child("chatrooms").child(chatRoomId).setValue(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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

    private void initMatchRecycler() {
        matchesAdapter = new MatchesAdapter(getContext(), matchResponseList, this);
        final RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,
                        false);

        mMatchesRecyclerView.setLayoutManager(mLayoutManager);
        mMatchesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMatchesRecyclerView.setNestedScrollingEnabled(false);
        mMatchesRecyclerView.setHasFixedSize(false);
        mMatchesRecyclerView.setAdapter(matchesAdapter);
    }

    private void getChatRooms() {
        chatrooms = new ArrayList<>();
        chatroomsSoFar = new HashSet<>();
        chatRoomAdapter = new ChatRoomAdapter(getContext(), chatrooms, listener);
        mRoomsRecycler.setAdapter(chatRoomAdapter);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mRoomsRecycler);
        FirebaseDatabase.getInstance().getReference().child("Users").child(BasePreferenceManager.getDefault().getFcmToken()).child("chatrooms")
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
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getContext().startActivity(new Intent(getActivity(),
                        ChatActivity.class).putExtra("chatRoomID", chatrooms.get(position)));
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 0);
    }

    @Override
    public void onChatRoomLongClicked(int position) {

    }

    @Override
    public void onMatchClicked(int position) {

    }

    @Subscribe(sticky = true)
    public void onEvent(final DeliverListMatchProfileBus matchProfileBus) {
        if (matchProfileBus.getToClass().equals(FindMatchActivity.class.getSimpleName())
                && matchProfileBus.getType() == DeliverListMatchProfileBus.Type.ACTUAL ) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    matchResponseList.addAll(matchProfileBus.getMatchProfileResponseList());
                    matchesAdapter.notifyDataSetChanged();
                }
            });

            EventBus.getDefault().removeStickyEvent(matchProfileBus);
        }
    }
}
