package com.example.dezvezesdez.getbusmotorista;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CheckTicketOflineActivity extends AppCompatActivity {

    private static BILHETE[] bilhetes;

    private String link_resultado_passado;

    private String IP_CLIENT;
    private String NUM_ID_CLIENT;
    private String NAME_CLIENT;
    private String PHONE_CLIENT;
    private String EMAIL_CLIENT;
    private String TYPE_TRIP;
    private String DESTINATION_TRIP;
    private String SOURCE_TRIP;
    private String NUM_CHILD;
    private String NUM_ADULT;
    private String DATE_GO;
    private String DATE_COME;
    private String TYPE_PAY;
    private String DATE_PAY;
    private String TOTAL_PAY;

    private String id;

    private String validation_id;
    private String validation_ida;
    private String validation_volta;

    private boolean isGoing;
    private boolean isComming;
    private boolean bt_ida_is_green;
    private boolean bt_ida_e_volta_is_green;

    private TextView tv_source;
    private TextView tv_destiny;
    private TextView tv_date_go;
    private TextView tv_date_come;
    private TextView tv_type_trip;
    private TextView tv_total_pay;
    private TextView tv_type_pay;
    private TextView tv_date_pay;
    private TextView tv_adult_seats;
    private TextView tv_childs_seats;
    private TextView tv_name_client;
    private TextView tv_ip_client;
    private TextView tv_phone_client;
    private TextView tv_email_client;

    private TextView name_client;
    private TextView ip_client;
    private TextView phone_client;
    private TextView email_client;

    private Button bt_ida;
    private Button bt_ida_e_volta;

    private DataBase_Controller db;

/*    private int red;
    private int green;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_ticket_ofline);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        db = new DataBase_Controller(getBaseContext());

        bt_ida = (Button) findViewById(R.id.bt_ida);
        bt_ida_e_volta = (Button) findViewById(R.id.bt_ida_e_volta);

        tv_source = (TextView) findViewById(R.id.tv_source);
        tv_destiny = (TextView) findViewById(R.id.tv_destiny);
        tv_date_go = (TextView) findViewById(R.id.tv_date_go);
        tv_date_come = (TextView) findViewById(R.id.tv_date_come);
        tv_type_trip = (TextView) findViewById(R.id.tv_type_trip);
        tv_total_pay = (TextView) findViewById(R.id.tv_total_pay);
        tv_type_pay = (TextView) findViewById(R.id.tv_type_pay);
        tv_date_pay = (TextView) findViewById(R.id.tv_date_pay);
        tv_adult_seats = (TextView) findViewById(R.id.tv_adult_seats);
        tv_childs_seats = (TextView) findViewById(R.id.tv_childs_seats);
        tv_name_client = (TextView) findViewById(R.id.tv_name_client);
        tv_ip_client = (TextView) findViewById(R.id.tv_ip_client);
        tv_phone_client = (TextView) findViewById(R.id.tv_phone_client);
        tv_email_client = (TextView) findViewById(R.id.tv_email_client);
        ip_client = (TextView) findViewById(R.id.ip_client);
        name_client = (TextView) findViewById(R.id.name_client);
        phone_client = (TextView) findViewById(R.id.phone_client);
        email_client = (TextView) findViewById(R.id.email_client);

        link_resultado_passado = getIntent().getStringExtra("string_result");
        id = GetIdFromLink(link_resultado_passado); // tirar o id do link que faz entrar na ativity

        bt_ida_is_green = false;
        bt_ida_e_volta_is_green = false;

        GetValidatorId(id);
        ColorSwicthInicial();
        ButtonChanges();
        QueryDadosPorId();
        PreencherTextViews();


    }

    private void ColorSwicthInicial() {

       /* red = ContextCompat.getColor(this, R.color.red);
        green = ContextCompat.getColor(this, R.color.green);*/

        if (isGoing) {
            //bt_ida.setBackgroundColor(green);
            bt_ida.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.chech_green_button, null));
            bt_ida_is_green = true;
        } else {
            //bt_ida.setBackgroundColor(red);
            bt_ida.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.chech_red_button, null));
            bt_ida_is_green = false;
        }

        if (isComming) {
            //bt_ida_e_volta.setBackgroundColor(green);
            bt_ida_e_volta.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.chech_green_button, null));
            bt_ida_e_volta_is_green = true;

        } else {
            // bt_ida_e_volta.setBackgroundColor(red);
            bt_ida_e_volta.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.chech_red_button, null));
            bt_ida_e_volta_is_green = false;
        }

    }

    private void ButtonChanges() {

        /*ColorDrawable bt_ida_buttonColor = (ColorDrawable) bt_ida.getBackground();
        ColorDrawable bt_ida_e_volta_buttonColor = (ColorDrawable) bt_ida_e_volta.getBackground();
        final int bt_ida_colorId = bt_ida_buttonColor.getColor();
        final int bt_ida_e_volta_colorId = bt_ida_e_volta_buttonColor.getColor();*/

        final Drawable green_d = ResourcesCompat.getDrawable(getResources(), R.drawable.chech_green_button, null);

        final Drawable.ConstantState bt_ida_drawable = bt_ida.getBackground().getConstantState();
        //final Drawable bt_ida_e_volta_drawable = bt_ida_e_volta.getCompoundDrawables()[0];
        final Drawable.ConstantState bt_ida_e_volta_drawable = bt_ida_e_volta.getBackground().getConstantState();


        bt_ida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bt_ida_is_green) {
                    //bt_ida.setBackgroundColor(red);
                    bt_ida.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.chech_red_button, null));
                    UpdateValidatorDataBase("ida", 0);
                }

            }
        });

        bt_ida_e_volta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bt_ida_e_volta_is_green) {
                    //bt_ida_e_volta.setBackgroundColor(red);
                    bt_ida_e_volta.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.chech_red_button, null));
                    UpdateValidatorDataBase("volta", 0);
                }

            }
        });

    }

    private void QueryDadosPorId() {

        Cursor cursor = db.carregaDadoById(Integer.parseInt(id));

        IP_CLIENT = cursor.getString(cursor.getColumnIndexOrThrow(Database.IP_CLIENT));
        NUM_ID_CLIENT = cursor.getString(cursor.getColumnIndexOrThrow(Database.NUM_ID_CLIENT)); //para fazer um if
        NAME_CLIENT = cursor.getString(cursor.getColumnIndexOrThrow(Database.NAME_CLIENT));
        PHONE_CLIENT = cursor.getString(cursor.getColumnIndexOrThrow(Database.PHONE_CLIENT));
        EMAIL_CLIENT = cursor.getString(cursor.getColumnIndexOrThrow(Database.EMAIL_CLIENT));
        TYPE_TRIP = cursor.getString(cursor.getColumnIndexOrThrow(Database.TYPE_TRIP));
        DESTINATION_TRIP = cursor.getString(cursor.getColumnIndexOrThrow(Database.DESTINATION_TRIP));
        SOURCE_TRIP = cursor.getString(cursor.getColumnIndexOrThrow(Database.SOURCE_TRIP));
        NUM_CHILD = cursor.getString(cursor.getColumnIndexOrThrow(Database.NUM_CHILD));
        NUM_ADULT = cursor.getString(cursor.getColumnIndexOrThrow(Database.NUM_ADULT));
        DATE_GO = cursor.getString(cursor.getColumnIndexOrThrow(Database.DATE_GO));
        DATE_COME = cursor.getString(cursor.getColumnIndexOrThrow(Database.DATE_COME));
        TYPE_PAY = cursor.getString(cursor.getColumnIndexOrThrow(Database.TYPE_PAY));
        DATE_PAY = cursor.getString(cursor.getColumnIndexOrThrow(Database.DATE_PAY));
        TOTAL_PAY = cursor.getString(cursor.getColumnIndexOrThrow(Database.TOTAL_PAY));

        //validação para mostrar os dados pessoais
        if (NUM_ID_CLIENT.equalsIgnoreCase("option-1")) {
            name_client.setVisibility(View.GONE);
            ip_client.setVisibility(View.GONE);
            phone_client.setVisibility(View.GONE);
            email_client.setVisibility(View.GONE);
            tv_name_client.setVisibility(View.GONE);
            tv_ip_client.setVisibility(View.GONE);
            tv_phone_client.setVisibility(View.GONE);
            tv_email_client.setVisibility(View.GONE);
        } else {
            name_client.setVisibility(View.VISIBLE);
            ip_client.setVisibility(View.VISIBLE);
            phone_client.setVisibility(View.VISIBLE);
            email_client.setVisibility(View.VISIBLE);
            tv_name_client.setVisibility(View.VISIBLE);
            tv_ip_client.setVisibility(View.VISIBLE);
            tv_phone_client.setVisibility(View.VISIBLE);
            tv_email_client.setVisibility(View.VISIBLE);
        }


    }

    private void PreencherTextViews() {

        //validaçao
        IP_CLIENT = VerificacaoNull(IP_CLIENT);
        NAME_CLIENT = VerificacaoNull(NAME_CLIENT);
        PHONE_CLIENT = VerificacaoNull(PHONE_CLIENT);
        EMAIL_CLIENT = VerificacaoNull(EMAIL_CLIENT);
        TYPE_TRIP = VerificacaoNull(TYPE_TRIP);
        SOURCE_TRIP = VerificacaoNull(SOURCE_TRIP);
        DESTINATION_TRIP = VerificacaoNull(DESTINATION_TRIP);
        NUM_ADULT = VerificacaoNull(NUM_ADULT);
        NUM_CHILD = VerificacaoNull(NUM_CHILD);
        DATE_GO = VerificacaoNull(DATE_GO);
        DATE_COME = VerificacaoNull(DATE_COME);
        TYPE_PAY = VerificacaoNull(TYPE_PAY);
        DATE_PAY = VerificacaoNull(DATE_PAY);
        TOTAL_PAY = VerificacaoNull(TOTAL_PAY);

        //colocar os valores nas TextViews
        tv_ip_client.setText(IP_CLIENT);
        tv_name_client.setText(NAME_CLIENT);
        tv_phone_client.setText(PHONE_CLIENT);
        tv_email_client.setText(EMAIL_CLIENT);
        tv_type_pay.setText(TYPE_TRIP);
        tv_source.setText(SOURCE_TRIP);
        tv_destiny.setText(DESTINATION_TRIP);
        tv_adult_seats.setText(NUM_ADULT);
        tv_childs_seats.setText(NUM_CHILD);
        tv_date_go.setText(DATE_GO);
        tv_date_come.setText(DATE_COME);
        tv_type_trip.setText(TYPE_PAY);
        tv_total_pay.setText(TOTAL_PAY);
        tv_date_pay.setText(DATE_PAY);


    }

    public String VerificacaoNull(String a) {
        if (a.equalsIgnoreCase("NULL")) {
            a = "Dados Indisponíveis";
        }
        return a;
    }

    private String GetIdFromLink(String link) {

        int startIndex_ = link.indexOf("post=") + 5;
        int endIndex_ = link.indexOf("&action");

        String id = link.substring(startIndex_, endIndex_);

        return id;
    }

    private void GetValidatorId(String id) {

        //flag
        isGoing = false;
        isComming = false;

        int id_int = Integer.parseInt(id);
        Cursor cursor = db.CarregaDadosdeValidaçãoporId(id_int);

        validation_id = cursor.getString(cursor.getColumnIndexOrThrow(Database.VALIDATOR_ID));
        validation_ida = cursor.getString(cursor.getColumnIndexOrThrow(Database.VALIDATOR_IDA));
        validation_volta = cursor.getString(cursor.getColumnIndexOrThrow(Database.VALIDATOR_VOLTA));


        if (validation_ida.equalsIgnoreCase("1")) {
            isGoing = true;
        }

        if (validation_volta.equalsIgnoreCase("1")) {
            isComming = true;
        }


    }

    private void UpdateValidatorDataBase(String tipo, int flag) {

        if (tipo.equalsIgnoreCase("ida") && flag == 0) {
            // ida fechada
            validation_ida = "0";

        } else if (tipo.equalsIgnoreCase("ida") && flag == 1) {
            // ida aberta
            validation_ida = "1";

        } else if (tipo.equalsIgnoreCase("volta") && flag == 0) {
            //volta fechada
            validation_volta = "0";

        } else if (tipo.equalsIgnoreCase("volta") && flag == 1) {
            //volta aberta
            validation_volta = "1";

        }

        DataBase_Controller.alteraRegistoValidator(id, validation_ida, validation_volta);

        Database_info_Upload banco = new Database_info_Upload(this);
        banco.InserirDado(this, "1", "adaptado");


    }


}
