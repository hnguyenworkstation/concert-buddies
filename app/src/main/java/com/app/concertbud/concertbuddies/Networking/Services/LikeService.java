package com.app.concertbud.concertbuddies.Networking.Services;

/**
 * Created by huongnguyen on 4/23/18.
 */

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class LikeService extends Service {

    private static final String NOTIFICATION_ID_EXTRA = "notificationId";
    private static final String IMAGE_URL_EXTRA = "imageUrl";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        //Saving action implementation

        return null;
    }
}