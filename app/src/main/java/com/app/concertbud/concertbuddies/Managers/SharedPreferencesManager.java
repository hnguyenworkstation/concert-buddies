package com.app.concertbud.concertbuddies.Managers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by huongnguyen on 3/3/18.
 */

public class SharedPreferencesManager {
    private SharedPreferences sharedPreferences;
    private int PRIVATE_MODE = 0;
    private final String USER_PREF = "HUONG";
    private final String JWT_TOKEN = "JWT_TOKEN";
    /* ******************************************
     * INITIALIZATION
     * ******************************************/
    private static SharedPreferencesManager instance;

    private SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        instance = new SharedPreferencesManager(context);
    }
    public static SharedPreferencesManager getDefault() {
        return instance;
    }

    /* ******************************************
     * USER INFO
     * ******************************************/
    public void setUserToken(String token) {
        sharedPreferences.edit().putString(JWT_TOKEN, token).apply();
    }

    public String getUserToken() {
        return sharedPreferences.getString(JWT_TOKEN, null);
    }
}
