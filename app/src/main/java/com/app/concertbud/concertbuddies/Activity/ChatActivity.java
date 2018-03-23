package com.app.concertbud.concertbuddies.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.concertbud.concertbuddies.AppControllers.BaseActivity;
import com.app.concertbud.concertbuddies.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;

/**
 * Created by huongnguyen on 3/22/18.
 */

public class ChatActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.send_btn)
    Button mSendButton;
    @BindView(R.id.edit_message)
    EditText mEditMsg;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Messages");
    }

    @Override
    public void onClick(View v) {
        
    }
}
