package com.app.concertbud.concertbuddies.ViewFragments;


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
import android.widget.Toast;

import com.app.concertbud.concertbuddies.Abstracts.OnChatRoomClickListener;
import com.app.concertbud.concertbuddies.Activity.ChatActivity;
import com.app.concertbud.concertbuddies.Adapters.ChatRoomAdapter;
import com.app.concertbud.concertbuddies.Helpers.AppUtils;
import com.app.concertbud.concertbuddies.Networking.Responses.Chatroom;
import com.app.concertbud.concertbuddies.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchesFragment extends Fragment implements OnChatRoomClickListener{
    @BindView(R.id.chat_recycler)
    RecyclerView mRoomsRecycler;

    private ChatRoomAdapter chatRoomAdapter;
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

        initChatRoomsRecycler();
    }

    private void initChatRoomsRecycler() {
        chatRoomAdapter = new ChatRoomAdapter(getContext(), this);

        final RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mRoomsRecycler.setLayoutManager(mLayoutManager);
        mRoomsRecycler.setItemAnimator(new DefaultItemAnimator());
        mRoomsRecycler.setNestedScrollingEnabled(false);
        mRoomsRecycler.setHasFixedSize(false);
        mRoomsRecycler.setAdapter(chatRoomAdapter);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mRoomsRecycler);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onChatRoomClicked(int position) {
        Toast.makeText(getContext(), "clicked at: " + position, Toast.LENGTH_SHORT).show();
        /* Chatrooms database ref */
        DatabaseReference chatRoomsRef = FirebaseDatabase.getInstance().getReference().child("Chatrooms");
        final String chatRoomId = chatRoomsRef.push().getKey();

        /* Users database ref */
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        usersRef.child(chatRoomId).setValue(new Chatroom(chatRoomId, "Katy", "John"));

        // TODO: update heroku

        /* Messages database ref */

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

    @Override
    public void onChatRoomLongClicked(int position) {

    }
}
