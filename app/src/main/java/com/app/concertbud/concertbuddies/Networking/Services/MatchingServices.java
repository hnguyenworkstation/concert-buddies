package com.app.concertbud.concertbuddies.Networking.Services;

import com.app.concertbud.concertbuddies.Networking.Body.EventRequestBody;
import com.app.concertbud.concertbuddies.Networking.Responses.MatchProfileResponse;
import com.app.concertbud.concertbuddies.Networking.Responses.MatchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by hungnguyen on 4/21/18.
 */

public interface MatchingServices {
    /*
    * Get List of Potential Matches
    * */
    @GET("/matches/get_potential_matches/")
    Call<List<MatchProfileResponse>> getPotentialMatches(@Query("event_id") String eventId,
                                                         @Query("fb_token") String facebookToken);

    /*
    * Get List of Potential Matches
    * */
    @GET("/matches/")
    Call<List<MatchProfileResponse>> getMatches(@Query("fb_token")
                                                        String facebookToken);

    /*
    * Swipe Right
    * */
    @POST("/matches/like")
    Call<MatchResponse> swiped(@Body EventRequestBody eventRequestBody);
}
