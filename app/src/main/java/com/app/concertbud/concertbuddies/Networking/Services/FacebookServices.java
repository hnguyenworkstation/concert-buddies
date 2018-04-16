package com.app.concertbud.concertbuddies.Networking.Services;

import com.app.concertbud.concertbuddies.Networking.Responses.CompleteFacebookUserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by csadmin on 3/15/2018.
 */

public interface FacebookServices {
    @GET("me")
    Call<CompleteFacebookUserResponse> getUserInformation(@Query("fields") String fields,
                                                          @Query("access_token") String access_token);
}
