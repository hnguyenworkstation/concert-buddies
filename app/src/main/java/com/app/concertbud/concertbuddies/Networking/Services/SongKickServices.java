package com.app.concertbud.concertbuddies.Networking.Services;

/**
 * Created by huongnguyen on 3/3/18.
 */

import com.app.concertbud.concertbuddies.Networking.Responses.CompleteConcertsResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SongKickServices {
    /**********************************
     * @Method: GET
     * @Name: getNearbyConcerts
     * @Argument: EventInfoRequest
     * @Purpose: get all events in nearby area
     ********************************** */
    @POST("/api/3.0/search/locations.json")
    Call<CompleteConcertsResponse> getNearbyConcerts (@Query("location") String location,
                                                      @Query("apikey") String api_key);
}
