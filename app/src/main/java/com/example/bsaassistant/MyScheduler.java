package com.example.bsaassistant;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

public class MyScheduler {

    public static void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, MyBackgroundService.class);
        JobInfo jobInfo = new JobInfo.Builder(0, serviceComponent)
                .setRequiresCharging(false) // Set your preferred constraints
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(30 * 24 * 60 * 60 * 1000L) // Repeat every 30 days (approximately one month)
                .build();

        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);
    }
}
