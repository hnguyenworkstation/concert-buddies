package com.app.concertbud.concertbuddies.Tasks.Configs.Jobs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.app.concertbud.concertbuddies.Activity.FindMatchActivity;
import com.app.concertbud.concertbuddies.Activity.MainActivity;
import com.app.concertbud.concertbuddies.EventBuses.DeliverMatchResponseBus;
import com.app.concertbud.concertbuddies.Networking.Body.EventRequestBody;
import com.app.concertbud.concertbuddies.Networking.NetContext;
import com.app.concertbud.concertbuddies.Networking.Responses.MatchResponse;
import com.app.concertbud.concertbuddies.Networking.Services.MatchingServices;
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
 * Created by hungnguyen on 4/21/18.
 */

public class PostSwipeTask extends Job {
    private String eventId;
    private boolean isLiked;

    public PostSwipeTask(String eventId, boolean isLiked) {
        super(new Params(JobPriority.HIGH).requireNetwork()
                .persist().groupBy(JobGroup.matching));
        this.eventId = eventId;
        this.isLiked = isLiked;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        MatchingServices services = NetContext.instance.create(MatchingServices.class);
        services.swiped(new EventRequestBody(eventId, AccessToken.getCurrentAccessToken().getToken(), isLiked))
                .enqueue(new Callback<MatchResponse>() {
                    @Override
                    public void onResponse(Call<MatchResponse> call, Response<MatchResponse> response) {
                        if (response.body() != null) {
                            EventBus.getDefault().postSticky(new DeliverMatchResponseBus(response.body(),
                                    FindMatchActivity.class.getSimpleName()));
                            EventBus.getDefault().postSticky(new DeliverMatchResponseBus(response.body(),
                                    MainActivity.class.getSimpleName()));
                        }
                    }

                    @Override
                    public void onFailure(Call<MatchResponse> call, Throwable t) {

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
