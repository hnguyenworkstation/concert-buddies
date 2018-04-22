package com.app.concertbud.concertbuddies.Tasks.Configs.Jobs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.app.concertbud.concertbuddies.Activity.FindMatchActivity;
import com.app.concertbud.concertbuddies.EventBuses.DeliverListMatchProfileBus;
import com.app.concertbud.concertbuddies.Networking.Body.EventRequestBody;
import com.app.concertbud.concertbuddies.Networking.NetContext;
import com.app.concertbud.concertbuddies.Networking.Responses.MatchProfileResponse;
import com.app.concertbud.concertbuddies.Networking.Services.MatchingServices;
import com.app.concertbud.concertbuddies.Tasks.Configs.JobProperty.JobGroup;
import com.app.concertbud.concertbuddies.Tasks.Configs.JobProperty.JobPriority;
import com.app.concertbud.concertbuddies.ViewFragments.MatchesFragment;
import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.facebook.AccessToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hungnguyen on 4/21/18.
 */
public class FetchMatchesTask extends Job {
    private String eventId;

    public FetchMatchesTask(String eventId) {
        super(new Params(JobPriority.HIGH).requireNetwork()
                .persist().groupBy(JobGroup.matching));
        this.eventId = eventId;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        MatchingServices services = NetContext.instance.create(MatchingServices.class);
        services.getMatches(new EventRequestBody(AccessToken.getCurrentAccessToken().getToken()))
                .enqueue(new Callback<List<MatchProfileResponse>>() {
                    @Override
                    public void onResponse(Call<List<MatchProfileResponse>> call,
                                           Response<List<MatchProfileResponse>> response) {
                        EventBus.getDefault().postSticky(new DeliverListMatchProfileBus(eventId,
                                response.body(), FindMatchActivity.class.getSimpleName(), DeliverListMatchProfileBus.Type.ACTUAL));

                        EventBus.getDefault().postSticky(new DeliverListMatchProfileBus(eventId,
                                response.body(), MatchesFragment.class.getSimpleName(), DeliverListMatchProfileBus.Type.ACTUAL));
                    }

                    @Override
                    public void onFailure(Call<List<MatchProfileResponse>> call, Throwable t) {

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

