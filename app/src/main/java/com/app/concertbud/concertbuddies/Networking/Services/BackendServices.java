package com.app.concertbud.concertbuddies.Networking.Services;

import retrofit2.Call;
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
    Call<Void> getUser(@Path("user_id") int user_id);

    @POST("/users/")
    Call<Void> postUser(@Query("name") String name, @Query("dob") String dateOfBirth,
                        @Query("gender") String gender, @Query("fb_token") String facebook_token);
}
