package com.app.concertbud.concertbuddies.Tasks.Configs.Jobs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.app.concertbud.concertbuddies.Networking.NetContext;
import com.app.concertbud.concertbuddies.Networking.Services.SongKickServices;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.Tasks.Configs.JobProperty.JobGroup;
import com.app.concertbud.concertbuddies.Tasks.Configs.JobProperty.JobPriority;
import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Created by huongnguyen on 3/3/18.
 */

public class FetchNearbyConcertsJob extends Job {

    /* User's Location information */
    double longtitude = 0.0;
    double latitude = 0.0;

    public FetchNearbyConcertsJob(int pageNum, double lng, double lat) {
        super(new Params(JobPriority.HIGH).requireNetwork().persist().groupBy(JobGroup.concert));
        longtitude = lng;
        latitude = lat;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRun() throws Throwable {
        SongKickServices service = NetContext.instance.create(SongKickServices.class);
        service.getNearbyConcerts(longtitude, latitude,
                getApplicationContext().getString(R.string.songkick_api_token));
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
