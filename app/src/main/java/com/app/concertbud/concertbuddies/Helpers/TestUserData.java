package com.app.concertbud.concertbuddies.Helpers;

import android.content.Context;

import com.app.concertbud.concertbuddies.Networking.Responses.BaseResponse;
import com.app.concertbud.concertbuddies.Networking.Responses.UserResponse;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by hungnguyen on 4/6/18.
 */

public class TestUserData {
    public static ArrayList<UserResponse> getFakeData(Context context) {
        BaseResponse<ArrayList<UserResponse>> model = null;
        try {
            model = new GsonBuilder().create().fromJson(
                    new InputStreamReader(context.getAssets().open("user.json")),
                    new TypeToken<BaseResponse<ArrayList<UserResponse>>>() {}.getType()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return model != null ? model.results : null;
    }
}