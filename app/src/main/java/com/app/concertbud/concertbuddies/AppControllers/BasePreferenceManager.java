package com.app.concertbud.concertbuddies.AppControllers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hungnguyen on 2/14/18.
 */
public class BasePreferenceManager {
    private final String FCM_TOKEN = "FCMTOKEN";

    private SharedPreferences sharedPreferences;
    private int PRIVATE_MODE = 0;

    // App behavior manager
    private final String IS_FIRST_LAUNCH = "is_first_launch";

    // User management fields
    private final String KEY_PREF = "5p46h@7";
    private final String USER_TOKEN = "USER_TOKEN";
    private final String REQUEST_UPDATE_INFO = "REQUEST_UPDATE_INFO";
    private final String USER_NAME = "USERNAME";
    private final String USER_PASSWORD = "PASSWORD";
    private final String USER_DISPLAY_NAME = "USER_DISPLAY_NAME";
    private final String USER_PROFILE_URL = "USER_PROFILE_URL";
    private final String USER_NICKNAME = "USER_NICKNAME";
    private final String REQUEST_USER_INFO = "REQUEST_USER_INFO";
    private final String USER_ID = "USER_ID";

    /* ******************************************
    * APPLICATION INITIALIZATION PREFERENCES
    * *******************************************/
    private static BasePreferenceManager instance;
    private boolean isFirstLaunch;

    private BasePreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(
                KEY_PREF, Context.MODE_PRIVATE
        );

        isFirstLaunch = true;
    }

    public static void init(Context context) {
        if (instance == null)
            instance = new BasePreferenceManager(context);
    }

    public static BasePreferenceManager getDefault() {
        return instance;
    }

    public void setFcmToken(String token) {
        sharedPreferences.edit().putString(FCM_TOKEN, token).apply();
    }

    public String getFcmToken() {
        return sharedPreferences.getString(FCM_TOKEN, null);
    }

    /* ******************************************
    * USER CONTENTS PREFERENCES
    * *******************************************/

    public void setUserToken(String token) {
        sharedPreferences.edit().putString(USER_TOKEN, token).apply();
    }

    public String getUserToken() {
        return sharedPreferences.getString(USER_TOKEN, null);
    }

    public void setRequestUpdateInfo(boolean isNeed) {
        sharedPreferences.edit().putBoolean(REQUEST_UPDATE_INFO, isNeed).apply();
    }

    public boolean isNeededToRequestInfo() {
        return sharedPreferences.getBoolean(REQUEST_UPDATE_INFO, true);
    }
    public String[] getCredentials() {
        String username = sharedPreferences.getString(USER_NAME, "");
        String password = sharedPreferences.getString(USER_PASSWORD, "");
        return new String[] {username, password};
    }

    public void setCredentials(String[] credentials) {
        sharedPreferences.edit().putString(USER_NAME, credentials[0]).apply();
        sharedPreferences.edit().putString(USER_PASSWORD, credentials[1]).apply();
    }


    /* ******************************************
    * APPLICATION BEHAVIOR PREFERENCES
    * *******************************************/
    public void setIsFirstLaunch(boolean isFirstLaunch) {
        sharedPreferences.edit().putBoolean(IS_FIRST_LAUNCH, isFirstLaunch).apply();
    }

    public boolean isFirstLaunch() {
        return sharedPreferences.getBoolean(IS_FIRST_LAUNCH, true);
    }
}
