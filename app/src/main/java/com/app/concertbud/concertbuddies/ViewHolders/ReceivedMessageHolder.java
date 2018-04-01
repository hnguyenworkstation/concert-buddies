package com.app.concertbud.concertbuddies.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.app.concertbud.concertbuddies.Networking.Responses.Message;
import com.app.concertbud.concertbuddies.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by huongnguyen on 4/1/18.
 */

public class ReceivedMessageHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_message_body)
    TextView msgText;
    @BindView(R.id.text_message_name)
    TextView msgName;
    @BindView(R.id.text_message_time)
    TextView msgTime;

    private Unbinder unbinder;

    public ReceivedMessageHolder(View itemView) {
        super(itemView);
        unbinder = ButterKnife.bind(this, itemView);
    }

    public void bind(Message msg) {
        setMsgName(msg.getSenderName());
        setMsgText(msg.getContent());
        setMsgTime(msg.getTimestamp());
    }

    private void setMsgText(String content) {
        msgText.setText(content);
    }

    private void setMsgName(String senderName) {
        msgName.setText(senderName);
    }

    private void setMsgTime(String timestamp) {
        Date date = new Date(Long.parseLong(timestamp));
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        msgTime.setText(formatter.format(date));
    }

    public void onRecycled() {
        unbinder.unbind();
    }
}
