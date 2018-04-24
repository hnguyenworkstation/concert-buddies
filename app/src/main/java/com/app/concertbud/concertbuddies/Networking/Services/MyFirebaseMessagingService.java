package com.app.concertbud.concertbuddies.Networking.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.app.concertbud.concertbuddies.Activity.ChatActivity;
import com.app.concertbud.concertbuddies.Activity.MainActivity;
import com.app.concertbud.concertbuddies.AppControllers.BasePreferenceManager;
import com.app.concertbud.concertbuddies.Helpers.ImageLoader;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.ViewFragments.MatchesFragment;
import com.facebook.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

/**
 * Created by huongnguyen on 4/22/18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static String TAG = "FirebaseMessagingService";
    private int numPendingMessages = 0;
    private int numMsgSeen = 0;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        String notificationTitle = null, notificationBody = null;
//        String senderId = remoteMessage.getData().get("senderId");
//        if (senderId.equals(FirebaseAuth.getInstance().getUid())) return;
//        Log.e("HUONG noti", "received noti");
//        // check if message contains payload
//        if (remoteMessage.getData() != null) {
//            notificationTitle = remoteMessage.getNotification().getTitle();
//            notificationBody = remoteMessage.getNotification().getBody();
//        }
//
//        sendNotification(notificationTitle, notificationBody);

        if (remoteMessage.getData().get("senderId").equals(FirebaseAuth.getInstance().getUid())) return;
        if (!isApplicationInForeground()) {
            /* Background */
            final String title = remoteMessage.getData().get("title");
            final String message = remoteMessage.getData().get("body");
            final String chatroomID = remoteMessage.getData().get("chatroomID");
            Log.e(TAG, "onMessageReceived chatroom: " + chatroomID);
            Query query = FirebaseDatabase.getInstance().getReference().child("Chatrooms")
                    .orderByKey().equalTo(chatroomID);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Object shot = dataSnapshot
                            .child(chatroomID + "/users/"+ BasePreferenceManager.getDefault().getFcmToken() + "/last_msg_seen").getValue(Integer.class);
                    try {
                        numMsgSeen = Integer.parseInt(shot.toString());
                    } catch (NullPointerException e) {
                        numMsgSeen = 0;
                    }
                    // get total num of messages
                    FirebaseDatabase.getInstance().getReference()
                            .child("Messages").child(chatroomID)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int numMessages = (int) dataSnapshot.getChildrenCount();
                            numPendingMessages = numMessages - numMsgSeen;
                            Log.e(TAG, "Number of pending messages are: " + numPendingMessages);
                            sendChatMessageNotification(title, message, chatroomID);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    private void sendChatMessageNotification(String title, String message, String chatroomID) {
        Log.e(TAG, "sendChatMsgNoti");
        // get notification id
        int notificationId = buildNotificationId(chatroomID);
        String channelId = getString(R.string.default_notification_channel_id);

        // instantiate builder object
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
        // create an intent for the activity
        Intent pendingIntent = new Intent(this, MainActivity.class);
        // set the activity to start in new, empty task
        pendingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pendingIntent.putExtra("intent_chatroom", chatroomID);
        // create the pendingintent
        PendingIntent notifyPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        pendingIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        // add properties to the builder
        builder.setSmallIcon(R.drawable.big_logo_image)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentTitle(title)
                .setColor(getColor(R.color.colorAccent))
                .setAutoCancel(true)
                .setContentText(message)
                .setNumber(numPendingMessages)
                .setOnlyAlertOnce(true);
        builder.setContentIntent(notifyPendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, builder.build());
    }

    private int buildNotificationId(String id) {
        Log.d(TAG, "buildNotificationId: building a notification id.");

        int notificationId = 0;
        for(int i = 0; i < 9; i++){
            notificationId = notificationId + id.charAt(0);
        }
        Log.d(TAG, "buildNotificationId: id: " + id);
        Log.d(TAG, "buildNotificationId: notification id:" + notificationId);
        return notificationId;
    }

    private boolean isApplicationInForeground() {
        Log.e(TAG, "App is in foreground: " + Boolean.toString(ChatActivity.isActivityRunning));
        return ChatActivity.isActivityRunning;
    }
//    private void sendNotification(String notificationTitle, String notificationBody) {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        String channelId = getString(R.string.default_notification_channel_id);
//
//        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
//                .setAutoCancel(true)   //Automatically delete the notification
//                .setSmallIcon(R.drawable.ic_explore_gray)
//                .setContentIntent(pendingIntent)
//                .setContentTitle(notificationTitle)
//                .setContentText(notificationBody)
//                .setSound(defaultSoundUri);
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(channelId,
//                    "Channel human readable title",
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//
//        notificationManager.notify(0, notificationBuilder.build());
//    }
}
