package com.app.concertbud.concertbuddies.Networking.Services;

/**
 * Created by huongnguyen on 3/3/18.
 */

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
    @POST("/api/3.0/search/locations.json?location=geo:{lat},{lng}")
    Call<Void> getNearbyConcerts (@Path("lat") double lat, @Path("lng") double lng,
                                  @Query("apikey") String api_key);
}
