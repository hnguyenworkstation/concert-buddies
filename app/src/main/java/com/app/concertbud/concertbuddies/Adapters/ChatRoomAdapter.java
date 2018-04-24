package com.app.concertbud.concertbuddies.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.concertbud.concertbuddies.Abstracts.OnChatRoomClickListener;
import com.app.concertbud.concertbuddies.Networking.Responses.Chatroom;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.ViewHolders.ChatRoomViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by hungnguyen on 3/3/18.
 */
public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomViewHolder> {
    private Context context;
    private OnChatRoomClickListener listener;
    private ArrayList<String> chatrooms;

    public ChatRoomAdapter(Context context, ArrayList<String> chatrooms, OnChatRoomClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.chatrooms = chatrooms;
    }

    @Override
    public ChatRoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_room_viewholder,
                        parent, false);
        return new ChatRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ChatRoomViewHolder holder, final int position) {
        // get current chatroom to bind view
        String chatRoomId = chatrooms.get(position);
        FirebaseDatabase.getInstance().getReference().child("Chatrooms").child(chatRoomId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Chatroom chatroom = dataSnapshot.getValue(Chatroom.class);
                        holder.init(chatroom, position, listener, holder.itemView.getContext().getResources());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void onViewRecycled(ChatRoomViewHolder holder) {
        super.onViewRecycled(holder);
        holder.onRecycled();
    }

    @Override
    public int getItemCount() {
        //return 20;
        return chatrooms.size();
    }
}