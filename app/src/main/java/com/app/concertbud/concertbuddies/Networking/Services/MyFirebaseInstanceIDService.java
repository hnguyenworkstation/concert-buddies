package com.app.concertbud.concertbuddies.Networking.Services;

import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by huongnguyen on 4/22/18.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseInstanceIdService";

    @Override
    public void onTokenRefresh() {
        // update backend on new Firebase token
    }
}
