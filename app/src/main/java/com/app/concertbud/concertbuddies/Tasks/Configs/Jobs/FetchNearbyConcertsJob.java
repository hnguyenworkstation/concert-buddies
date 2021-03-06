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
import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huongnguyen on 3/3/18.
 */

public class FetchNearbyConcertsJob extends Job {

    /* User's Location information */
    private String queryLocation;
    private int pageNum;
    private boolean newLocation = false;
    private int DEFAULT_RADIUS = 50;
    private String DEFAULT_SEGMENT = "Music";
    private String DEFAULT_SORT_OPTION = "date,asc";

    public FetchNearbyConcertsJob(int pageNum, double lng, double lat, boolean newLocation) {
        super(new Params(JobPriority.HIGH).requireNetwork().persist().groupBy(JobGroup.concert));
        queryLocation = Double.toString(lat) + "," + Double.toString(lng);
        this.pageNum = pageNum;
        this.newLocation = newLocation;
    }

    @Override
    public void onRun() throws Throwable {
        TicketMasterServices service = TicketMasterContext.instance.create(TicketMasterServices.class);
        service.getConcertsNearby(DEFAULT_RADIUS, queryLocation, DEFAULT_SORT_OPTION,
                DEFAULT_SEGMENT, pageNum, getApplicationContext().getString(R.string.ticket_master_api_token))
                .enqueue(new Callback<CompleteTMConcertsResponse>() {
                    @Override
                    public void onResponse(Call<CompleteTMConcertsResponse> call, Response<CompleteTMConcertsResponse> response) {
                        if (response.code() == 200) {
                            if (response.body().getEmbedded() != null) {
                                List<EventsEntity> concerts = response.body().getEmbedded().getEvents();
                                EventBus.getDefault().postSticky(new ConcertsNearbyBus(concerts, newLocation, MapFragment.class.getSimpleName()));
                                EventBus.getDefault().postSticky(new ConcertsNearbyBus(concerts, newLocation, ListSearchEventFragment.class.getSimpleName()));
                            }
                            else { /* reach the end */
                                // TODO: double check if this will update subscribing components
                                EventBus.getDefault().postSticky(new ConcertsNearbyBus(null, newLocation, MapFragment.class.getSimpleName()));
                                EventBus.getDefault().postSticky(new ConcertsNearbyBus(null, newLocation, ListSearchEventFragment.class.getSimpleName()));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CompleteTMConcertsResponse> call, Throwable t) {

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