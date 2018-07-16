package com.example.dezvezesdez.getbusmotorista;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by dezvezesdez on 30/05/16.
 */
public class FragmentActivity_Loading extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_loading);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        boolean firstTime = Helper_APi.FirstTimeOpenApp(this);

        if(firstTime){
            new DownLoadDataBase(FragmentActivity_Loading.this, this).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, (Void) null);
        }


        if(getIntent().getExtras() == null){
            new AsyncTask_SplashScreen(FragmentActivity_Loading.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void) null);

        }else if(getIntent().getStringExtra("download").equals("download_flag")){

            new DownLoadDataBase(FragmentActivity_Loading.this, this).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, (Void) null);

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }
}
