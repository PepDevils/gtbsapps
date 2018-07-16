package com.example.dezvezesdez.getbusmotorista;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    public String link_resultado;
    public String link_resultado_clean;
    public String isDataUpLoaded;
    protected String message_retriver;
    protected boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        registerReceiver(broadcastReceiver, new IntentFilter("Broad_CastName_Receiver_Connection"));

        message_retriver = "initial";

        final Activity activity = this;

        Button bt_scannear = (Button) findViewById(R.id.bt_scannear);
        Button bt_upload_data = (Button) findViewById(R.id.bt_upload_data);
        Button bt_download_data = (Button) findViewById(R.id.bt_download_data);

        Initial_logIn();

        VerifyStates_ChangeCircles();

        final View v_database = findViewById(R.id.v_database);
        assert v_database != null;
        v_database.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.circle_red, null));




        if (getIntent() != null) {
            if (getIntent().getExtras() != null) {
                String flag = getIntent().getStringExtra("flag_tag");
                Toast.makeText(MainActivity.this, flag, Toast.LENGTH_LONG).show();
                if(flag.equalsIgnoreCase("Base de dados actualizada com sucesso")){
                    v_database.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.circle_green, null));
                }

            }
        }


        assert bt_scannear != null;
        bt_scannear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inicializa o scanner de qrcode
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scanear");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }


        });

        assert bt_download_data != null;
        bt_download_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isDataUpLoaded = UpLoadedDataString();

                if (isDataUpLoaded.equalsIgnoreCase("NULL")) {
                    InicializarAppComDownLoad();
                } else if (isDataUpLoaded.equalsIgnoreCase("DataBaseUploaded")) {
                    InicializarAppComDownLoad();
                    Database_info_Upload banco = new Database_info_Upload(activity);
                    banco.InserirDado(activity, "1", null);

                } else {
                    Toast.makeText(MainActivity.this, "Tem dados pendentes...", Toast.LENGTH_LONG).show();
                    Toast.makeText(MainActivity.this, "CLIQUE EM ENVIAR E RECEBER DADOS", Toast.LENGTH_LONG).show();

                }


            }
        });

        assert bt_upload_data != null;
        bt_upload_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Tem a certeza ? ")
                        .setContentText("Que quer enviar as informações para a base de dados")
                        .setCancelText("Não")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .setConfirmText("Sim")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                String noticia = "erro";

                                if (isOnline() && DataBase_Controller.checkDataBase(MainActivity.this)) {

                                    SQLiteDatabase myDB;
                                    myDB = MainActivity.this.openOrCreateDatabase(Database.DATABASE_NAME, 0, null);

                                    Cursor cursor = null;
                                    try {
                                        cursor = myDB.rawQuery("SELECT " + Database.POST_ID + " FROM " + Database.TABELA_VALIDATOR, null);
                                    } catch (Exception e) {
                                        Toast.makeText(MainActivity.this, "Operação indisponível", Toast.LENGTH_SHORT).show();
                                        //e.printStackTrace();
                                    }

                                    String[] array = new String[cursor.getCount()];
                                    int i = 0;
                                    while (cursor.moveToNext()) {
                                        String uname = cursor.getString(cursor.getColumnIndex(Database.POST_ID));
                                        array[i] = uname;
                                        i++;
                                    }

                                    int count = array.length;
                                    for (int j = 0; j < count; j++) {

                                        boolean flag_send = Helper_APi.SendValidatorId_to_Api(array[j]);

                                        if (flag_send) {
                                            Database_info_Upload banco = new Database_info_Upload(activity);
                                            banco.InserirDado(activity, "1", "DataBaseUploaded");

                                            noticia = "Envio de dados com sucesso";


                                        } else {
                                            noticia = "Envio de dados sem sucesso";
                                            break;
                                        }

                                    }


                                    if (noticia.equalsIgnoreCase("Envio de dados com sucesso")) {

                                        InicializarAppComDownLoad();
                                        Database_info_Upload banco = new Database_info_Upload(activity);
                                        banco.InserirDado(activity, "1", null);

                                    }else{
                                        Toast.makeText(MainActivity.this, "Operação indisponível", Toast.LENGTH_SHORT).show();
                                    }

                                }else{
                                    Toast.makeText(MainActivity.this, "Operação indisponível", Toast.LENGTH_SHORT).show();
                                }

                                sDialog.cancel();
                            }
                        })
                        .show();
            }
        });


    }//fim onCreat


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("MainActivity", " SCANEAMENTO CANCELADO ");
                Toast.makeText(this, "CANCELADO", Toast.LENGTH_LONG).show();
            } else {
                //quando o scan tem resultado

                //string com o resultado
                link_resultado = result.getContents();

                //tratamento da string para ter apenas o link certo
                if (null != link_resultado && link_resultado.length() > 0) {
                    int startIndex = link_resultado.indexOf("http");
                    if (startIndex != -1) {
                        link_resultado_clean = link_resultado.substring(startIndex, link_resultado.length()); // not forgot to put check if(endIndex != -1)
                    }
                }

                //se tem conexao abre em webview wordpress, caso não, em activity normal da aplicação
                if (isOnline()) {

                    Uri uri = Uri.parse(link_resultado_clean);
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(i);

                } else {

                    if (DataBase_Controller.checkDataBase(this)) {

                        //entra na activity que posteriormente faz uma upload para a base de dados
                        Intent i = new Intent(this, CheckTicketOflineActivity.class);
                        i.putExtra("string_result", link_resultado_clean);
                        startActivityForResult(i, 1);

                    } else {
                        Toast.makeText(MainActivity.this, "Não foi encontrada nenhuma base de dados no dispositivo... ", Toast.LENGTH_LONG).show();
                        Toast.makeText(MainActivity.this, "CLIQUE EM RECEBER DADOS", Toast.LENGTH_LONG).show();

                    }
                }
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);

        }
    }

    public boolean isOnline() {

        if (message_retriver.equals("initial")) {

            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo net = connManager.getActiveNetworkInfo();
            connected = (net != null && net.isConnected());
            return connected;

        } else {

            if (message_retriver.equals("true")) {
                connected = true;
            } else {
                connected = false;
            }

            return connected;
        }

    }

    public void InicializarAppComDownLoad() {
        Intent intent = new Intent(MainActivity.this, FragmentActivity_Loading.class);
        intent.putExtra("download", "download_flag");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    public String UpLoadedDataString() {
        Database_info_Upload db = new Database_info_Upload(this);
        Cursor cursor = db.BuscarDado(this);
        isDataUpLoaded = cursor.getString(1);
        return isDataUpLoaded;
    }

    public void VerifyStates_ChangeCircles() {

        View v_connection = findViewById(R.id.v_connection);


        assert v_connection != null;
        v_connection.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.circle_red, null));

        if (isOnline()) {
            v_connection.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.circle_green, null));
        }

    }


    //recebe as actualizações do Broad_CastName_Receiver_Connection  e passa dados via Intent

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = intent.getExtras();
            message_retriver = b.getString("message");
            VerifyStates_ChangeCircles();
        }
    };

    private void Initial_logIn(){



    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(broadcastReceiver);

    }


}
