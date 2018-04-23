package com.app.concertbud.concertbuddies.Networking.Services;

import android.util.Log;

import com.app.concertbud.concertbuddies.Activity.LoginActivity;
import com.app.concertbud.concertbuddies.AppControllers.BasePreferenceManager;
import com.app.concertbud.concertbuddies.Networking.NetContext;
import com.facebook.Profile;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huongnguyen on 4/22/18.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseInstIdService";

    @Override
    public void onTokenRefresh() {
        // update backend on new Firebase token
        final String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, refreshedToken);
        BasePreferenceManager.getDefault().setFcmToken(refreshedToken);
//        BackendServices backendServices = NetContext.instance.create(BackendServices.class);
//        backendServices.updateFCMToken(new UpdateFCMRequest(Profile.getCurrentProfile().getId(), refreshedToken))
//                .enqueue(new Callback<Void>() {
//                    @Override
//                    public void onResponse(Call<Void> call, Response<Void> response) {
//                        if (response.code() == 200) {
//                            Log.e(TAG, "successfully updated Heroku with fcm token: " + refreshedToken);
//                        }
//                        else {
//                            Log.e(TAG, "error updating Heroku with fcm token: " + refreshedToken);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Void> call, Throwable t) {
//
//                    }
//                });
    }
}
