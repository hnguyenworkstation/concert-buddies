package com.app.concertbud.concertbuddies.Tasks.Configs.Jobs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.app.concertbud.concertbuddies.EventBuses.ConcertsNearbyBus;
import com.app.concertbud.concertbuddies.Networking.Responses.CompleteTMConcertsResponse;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.EventsEntity;
import com.app.concertbud.concertbuddies.Networking.Services.TicketMasterServices;
import com.app.concertbud.concertbuddies.Networking.TicketMasterContext;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.Tasks.Configs.JobProperty.JobGroup;
import com.app.concertbud.concertbuddies.Tasks.Configs.JobProperty.JobPriority;
import com.app.concertbud.concertbuddies.ViewFragments.ListSearchEventFragment;
import com.app.concertbud.concertbuddies.ViewFragments.MapFragment;
import com.app.concertbud.concertbuddies.ViewFragments.SubscribedEventsFragment;
import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cmunroe on 4/17/18.
 */

public class GetEventJob extends Job {

    final String eventId;
    final int index;

    public GetEventJob(String eventId, int index) {
        super(new Params(JobPriority.HIGH).requireNetwork().persist().groupBy(JobGroup.concert));
        this.eventId = eventId;
        this.index = index;
    }

    @Override
    public void onRun() throws Throwable {
        TicketMasterServices service = TicketMasterContext.instance.create(TicketMasterServices.class);
        service.getEvent(eventId, getApplicationContext().getString(R.string.ticket_master_api_token))
                .enqueue(new Callback<EventsEntity>() {
                    @Override
                    public void onResponse(Call<EventsEntity> call, Response<EventsEntity> response) {
                        if (response.code() == 200) {
                            SubscribedEventsFragment.self.addEventCard(response.body(), index);
                        }
                    }

                    @Override
                    public void onFailure(Call<EventsEntity> call, Throwable t) {
                        Log.e("chris", "couldnt parse " + t.getMessage());
                    }
                });
    }

    @Override
    public void onAdded() {

    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }

}