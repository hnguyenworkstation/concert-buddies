package com.app.concertbud.concertbuddies.Networking.Services;

import com.app.concertbud.concertbuddies.Networking.Body.EventRequestBody;
import com.app.concertbud.concertbuddies.Networking.Heroku.JoiningEventResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

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
}
