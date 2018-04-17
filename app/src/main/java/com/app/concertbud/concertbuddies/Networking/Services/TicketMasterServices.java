package com.app.concertbud.concertbuddies.Networking.Services;

import com.app.concertbud.concertbuddies.Networking.Responses.CompleteTMConcertsResponse;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.EventsEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
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


    @GET("/discovery/v2/events/{eventId}")
    Call<EventsEntity> getEvent(@Path("eventId") String eventId,
                                @Query("apikey") String api_key);
}
