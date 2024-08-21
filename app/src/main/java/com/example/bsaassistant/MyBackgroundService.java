package com.example.bsaassistant;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.bsaassistant.databases.JamaKharachDbHelper;
import com.example.bsaassistant.share.ShareDbHelper;
import com.example.bsaassistant.share.ShareTransactionDbHelper;
import com.example.bsaassistant.share.shareDataModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class MyBackgroundService extends JobService {




    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        // This code will be executed every time the service is started
        ShareDbHelper shareDbHelper = new ShareDbHelper(getBaseContext());
        ArrayList<shareDataModel> shareDataModels = new ArrayList<>();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM-yyyy"));


            shareDataModels = shareDbHelper.getShares();
            for (int i = 0; i < shareDataModels.size(); i++) {
                shareDataModel shareDataModel = shareDataModels.get(i);
                ShareTransactionDbHelper shareTransactionDbHelper = new ShareTransactionDbHelper(getBaseContext());
                shareTransactionDbHelper.addShareTransaction(shareDataModel.getPatPedhiName(), shareDataModel.getEmi(), date);

            }
        }
        //  }if  you want to perform the function once every month, you can use a counter or

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }


}
