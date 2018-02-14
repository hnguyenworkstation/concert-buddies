package com.app.concertbud.concertbuddies.Tasks.Configs;

import android.support.annotation.NonNull;

import com.app.concertbud.concertbuddies.AppControllers.BaseApplication;
import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.scheduling.GcmJobSchedulerService;

/**
 * Created by hungnguyen on 2/14/18.
 */

public class AppGcmTaskService extends GcmJobSchedulerService {
    @NonNull
    @Override
    protected JobManager getJobManager() {
        return BaseApplication.getInstance().getJobManager();
    }
}