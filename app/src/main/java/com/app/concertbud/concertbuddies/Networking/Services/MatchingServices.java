package com.app.concertbud.concertbuddies.Networking.Services;

import com.app.concertbud.concertbuddies.Networking.Body.EventRequestBody;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.ListEventsEntity;
import com.app.concertbud.concertbuddies.Networking.Responses.MatchProfileResponse;
import com.app.concertbud.concertbuddies.Networking.Responses.MatchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by hungnguyen on 4/21/18.
 */

public interface MatchingServices {
    /*
    * Get List of Potential Matches
    * */
    @GET("/matches/get_potential_matches/")
    Call<List<MatchProfileResponse>> getPotentialMatches(@Body EventRequestBody eventRequestBody);

    /*
    * Get List of Potential Matches
    * */
    @GET("/matches/")
    Call<List<MatchProfileResponse>> getMatches(@Body EventRequestBody eventRequestBody);

    /*
    * Swipe Right
    * */
    @POST("/matches/like/")
    Call<MatchResponse> swiped(@Body EventRequestBody eventRequestBody);
}
