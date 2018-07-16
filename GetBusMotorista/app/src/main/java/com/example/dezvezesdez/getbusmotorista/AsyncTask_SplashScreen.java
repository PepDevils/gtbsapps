package com.example.dezvezesdez.getbusmotorista;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by dezvezesdez on 23/05/16.
 */
public class AsyncTask_SplashScreen extends AsyncTask<Void, Void, Void> {

    private Intent intent;
    private Activity activity;


    public AsyncTask_SplashScreen(Activity a){
        this.activity= a;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... params) {

        boolean existeDB = Database_info_Upload.checkDataBase(activity);

        if(!existeDB){
            Database_info_Upload banco = new Database_info_Upload(activity);
            banco.InserirDado(activity,"1",null);
        }


        processamentoPrincipal();

        return null;
    }


    @Override
    protected void onPostExecute(Void o) {
        super.onPostExecute(o);

        intent = new Intent(activity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);


    }




    public void processamentoPrincipal() {

        try {

            int time_secondes = 2;
            int time_miliseconds = time_secondes * 1000;
            Thread.sleep(time_miliseconds);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
