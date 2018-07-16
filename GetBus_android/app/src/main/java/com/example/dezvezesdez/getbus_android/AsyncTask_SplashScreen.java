package com.example.dezvezesdez.getbus_android;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by dezvezesdez on 05/05/16.
 */
public class AsyncTask_SplashScreen extends AsyncTask<Object, Integer, Object> {

    // VARIAVEIS
    private Activity activity;
    private Intent i;
    private ArrayList<String> destinos_chegadas;

    //CONSTRUTOR
    public AsyncTask_SplashScreen(Activity activity) {
        this.activity = activity;
    }

    // METODOS OBRIGATORIOS
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        //array List para o spinner
        destinos_chegadas = new ArrayList<>(Helper_APi.CarregarPartidasDestinos());

        i = new Intent(activity, Activity_Bilheteira.class);
        i.putExtra("destinos_chegadas", destinos_chegadas);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(i);
    }

    @Override
    protected Object doInBackground(Object... arg0) {

        processamentoPrincipal();

        return null;
    }


    // OUTROS METODOS
    public void processamentoPrincipal() {

        try {

            //carregar todos os componentes graficos da proxima activity aqui


            //tempo até entrar em onPostExecute
            int time_secondes = 1;
            int time_miliseconds = time_secondes * 1000;
            Thread.sleep(time_miliseconds);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
