package com.app.concertbud.concertbuddies.Tasks.Configs.Jobs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.app.concertbud.concertbuddies.Networking.NetContext;
import com.app.concertbud.concertbuddies.Networking.Services.FacebookServices;
import com.app.concertbud.concertbuddies.Tasks.Configs.JobProperty.JobGroup;
import com.app.concertbud.concertbuddies.Tasks.Configs.JobProperty.JobPriority;
import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

/**
 * Created by csadmin on 3/15/2018.
 */

public class PostUserInfo extends Job {
    private final String TAG = PostUserInfo.class.getSimpleName();
    private String name;
    private String dateOfBirth;

    public PostUserInfo(String name, String dateOfBirth) {
        super(new Params(JobPriority.HIGH).requireNetwork().persist().groupBy(JobGroup.concert));
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        FacebookServices services = NetContext.instance.create(FacebookServices.class);
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }

}
