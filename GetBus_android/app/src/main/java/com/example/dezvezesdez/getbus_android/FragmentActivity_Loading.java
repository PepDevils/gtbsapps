package com.example.dezvezesdez.getbus_android;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by dezvezesdez on 05/05/16.
 */
public class FragmentActivity_Loading extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_activity_loading);

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        new AsyncTask_SplashScreen(this).execute();

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }


}
