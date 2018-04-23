package com.app.concertbud.concertbuddies.Networking.Services;

import com.app.concertbud.concertbuddies.Networking.Requests.NewUserRequest;
import com.app.concertbud.concertbuddies.Networking.Responses.HerokuUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by csadmin on 3/15/2018.
 */

public interface BackendServices {
    @GET("/users/{user_id}")
    Call<HerokuUser> getUser(@Path("user_id") String user_id);

    @POST("/users")
    Call<Void> postUser(@Body NewUserRequest request);
//
//    @POST("/users/set_firebase_token")
//    Call<Void> updateFCMToken(@Body UpdateFCMRequest request);
}
