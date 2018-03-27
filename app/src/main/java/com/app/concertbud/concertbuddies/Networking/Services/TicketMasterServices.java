package com.app.concertbud.concertbuddies.Networking.Services;

import com.app.concertbud.concertbuddies.Networking.Responses.CompleteTMConcertsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by huongnguyen on 3/14/18.
 */

public interface TicketMasterServices {
    @GET("/discovery/v2/events.json")
    Call<CompleteTMConcertsResponse> getConcertsNearby(@Query("radius") int radius,
                                                       @Query("geoPoint") String geo_point,
                                                       @Query("sort") String sort_option,
                                                       @Query("segmentName") String segment,
                                                       @Query("page") int pageNum,
                                                       @Query("apikey") String api_key);
}
