package com.app.concertbud.concertbuddies.Networking.Services;

import com.app.concertbud.concertbuddies.Networking.Body.EventRequestBody;
import com.app.concertbud.concertbuddies.Networking.Heroku.JoiningEventResponse;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.ListEventsEntity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by hungnguyen on 4/15/18.
 */

public interface EventServices {
    /*
    * Join an Event
    * */
    @POST("/events/join/")
    Call<JoiningEventResponse> joinEvent(@Body EventRequestBody eventRequestBody);

    /*
    * Leave an Event
    * */
    @POST("/events/leave/")
    Call<JoiningEventResponse> leaveEvent(@Body EventRequestBody eventRequestBody);

    /*
    * Join an Event
    * */
    @GET("/events/list/{user_id}")
    Call<ListEventsEntity> getEvents(@Path("user_id") String user_id);
}
