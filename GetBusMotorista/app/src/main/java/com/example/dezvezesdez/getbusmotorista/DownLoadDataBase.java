package com.example.dezvezesdez.getbusmotorista;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.widget.Toast;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by dezvezesdez on 23/05/16.
 */
public class DownLoadDataBase extends AsyncTask<Void, Void, Void> {


    private Activity activity;
    private Context context;
    private String flag;
    private Intent intent;
    private BILHETE[] bib;
    private BILHETE[] bib_validator;

    //construtor
    public DownLoadDataBase(Activity a, Context c) {
        this.activity = a;
        this.context = c;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //carregar dados da API
        bib = Helper_APi.GetTicketDataBaseFromApi();
        bib_validator = Helper_APi.GetValidationTicket();


    }

    @Override
    protected Void doInBackground(Void... params) {
        Looper.prepare();

        DataBase_Controller dt_c = new DataBase_Controller(activity);

        if (bib != null && bib_validator != null) {

            int count;
            if (bib.length < bib_validator.length) {
                count = bib_validator.length;
            } else {
                count = bib.length;
            }


            for (int i = 0; i < count; i++) {
                dt_c.InsertDataBaseValidator(bib_validator[i]);
                dt_c.InsertDataBase(bib[i]);
            }

            flag =  "Base de dados actualizada com sucesso";

        } else {
            flag =  "Não tem conexão á internet, faça o download dos dados assim que tiver";
            //activity.finish();
        }

        return null;
    }


    @Override
    protected void onPostExecute(Void o) {
        super.onPostExecute(o);

        intent = new Intent(activity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("flag_tag", flag);
        activity.startActivity(intent);


    }
}
