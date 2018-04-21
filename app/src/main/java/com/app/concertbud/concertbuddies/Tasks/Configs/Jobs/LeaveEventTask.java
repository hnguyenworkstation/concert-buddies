package com.app.concertbud.concertbuddies.Tasks.Configs.Jobs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.app.concertbud.concertbuddies.EventBuses.BadRequestBus;
import com.app.concertbud.concertbuddies.EventBuses.JoinedEventSuccessBus;
import com.app.concertbud.concertbuddies.EventBuses.LeftEventSuccessBus;
import com.app.concertbud.concertbuddies.EventBuses.TaskFailedBus;
import com.app.concertbud.concertbuddies.Networking.Body.EventRequestBody;
import com.app.concertbud.concertbuddies.Networking.Heroku.JoiningEventResponse;
import com.app.concertbud.concertbuddies.Networking.NetContext;
import com.app.concertbud.concertbuddies.Networking.Services.EventServices;
import com.app.concertbud.concertbuddies.Tasks.Configs.JobProperty.JobGroup;
import com.app.concertbud.concertbuddies.Tasks.Configs.JobProperty.JobPriority;
import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.facebook.AccessToken;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hungnguyen on 4/15/18.
 */

public class LeaveEventTask extends Job {
    private String eventId;

    public LeaveEventTask(String eventId) {
        super(new Params(JobPriority.HIGH)
                .persist()
                .requireNetwork()
                .groupBy(JobGroup.concert));
        this.eventId = eventId;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        EventServices services = NetContext.instance.create(EventServices.class);
        services.leaveEvent(new EventRequestBody(eventId, AccessToken.getCurrentAccessToken().getToken()))
                .enqueue(new Callback<JoiningEventResponse>() {
                    @Override
                    public void onResponse(Call<JoiningEventResponse> call,
                                           Response<JoiningEventResponse> response) {
                        Log.e("JOIN EVENT", "onResponse: " +  response.toString());

                        if (response.code() == 200) {
                            EventBus.getDefault().post(new LeftEventSuccessBus());
                        } else {
                            EventBus.getDefault().post(new TaskFailedBus());
                        }
                    }

                    @Override
                    public void onFailure(Call<JoiningEventResponse> call, Throwable t) {
                        EventBus.getDefault().post(new BadRequestBus());
                    }
                });
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}
