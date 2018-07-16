package com.example.dezvezesdez.getbusmotorista;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;

import java.text.Normalizer;


/**
 * Created by dezvezesdez on 23/05/16.
 */
public class Helper_APi {

    static String baseUrl = "http://dezvezesdez.eu/getbus/app/mobile_api.php?func";

    public static boolean SendValidatorId_to_Api(String post_id) {

        boolean flag;

        try {

            Cursor cursor = DataBase_Controller.CarregaDadosdeValidaçãoporId(Integer.parseInt(post_id));

            String validator_ida = cursor.getString(cursor.getColumnIndexOrThrow(Database.VALIDATOR_IDA));
            String validator_volta = cursor.getString(cursor.getColumnIndexOrThrow(Database.VALIDATOR_VOLTA));

            if(validator_ida.equalsIgnoreCase("1")){
                validator_ida = "true";
            }else{
                validator_ida = "false";
            }

            if(validator_volta.equalsIgnoreCase("1")){
                validator_volta = "true";
            }else{
                validator_volta = "false";
            }


            String url = baseUrl + "=update_validation_db" + "&post_id=" + post_id + "&validator_ida=" + validator_ida + "&validator_volta=" + validator_volta;

            GetStringFromURL(url);
            flag = true;

        } catch (Exception e) {
            flag = false;
            // e.printStackTrace();

        }


        return flag;

    }


    public static BILHETE[] GetTicketDataBaseFromApi() {

        String url = baseUrl + "=get_tickets_db";


        BILHETE[] bib = null;

        try {
            JSONObject oJson = new JSONObject(GetStringFromURL(url));
            JSONObject oJson_tickets = oJson.getJSONObject("bilhetes");

            bib = new BILHETE[(oJson_tickets.getJSONArray("origem")).length()];

            int count = bib.length;

            for (int i = 0; i < count; i++) {

                String[] origem_bilhetes = Dividir_json_porCapitulo(oJson_tickets, "origem");
                String[] origem_bilhetes_clean = TratamentoVariaveisBilheteDB(origem_bilhetes[i]);

                String p_bilhetes = Dividir_json_porCapitulo_simples(oJson_tickets, "meto_pagamento");
                String p_bilhetes_clean = TratamentoVariaveisBilheteDB_simples(p_bilhetes);

                String destino_bilhetes = Dividir_json_porCapitulo_simples(oJson_tickets, "destino");
                String destino_bilhetes_clean = TratamentoVariaveisBilheteDB_simples(destino_bilhetes);


                String ip_client_bilhetes = Dividir_json_porCapitulo_simples(oJson_tickets, "ip_client");
                String ip_client_bilhetes_clean = TratamentoVariaveisBilheteDB_simples(ip_client_bilhetes);

                String first_name_bilhetes = Dividir_json_porCapitulo_simples(oJson_tickets, "first_name");
                String first_name_bilhetes_clean = TratamentoVariaveisBilheteDB_simples(first_name_bilhetes);

                String last_name_bilhetes = Dividir_json_porCapitulo_simples(oJson_tickets, "last_name");
                String last_name_bilhetes_clean = TratamentoVariaveisBilheteDB_simples(last_name_bilhetes);

                String bilhete_email_client_bilhetes = Dividir_json_porCapitulo_simples(oJson_tickets, "bilhete_email_client");
                String bilhete_email_client_bilhetes_clean = TratamentoVariaveisBilheteDB_simples(bilhete_email_client_bilhetes);

                String bilhete_phone_client_bilhetes = Dividir_json_porCapitulo_simples(oJson_tickets, "bilhete_phone_client");
                String bilhete_phone_client_bilhetes_clean = TratamentoVariaveisBilheteDB_simples(bilhete_phone_client_bilhetes);

                String bilhete_total_pay_bilhetes = Dividir_json_porCapitulo_simples(oJson_tickets, "bilhete_total_pay");
                String bilhete_total_pay_bilhetes_clean = TratamentoVariaveisBilheteDB_simples(bilhete_total_pay_bilhetes);

                String bilhete_num_id_client_bilhetes = Dividir_json_porCapitulo_simples(oJson_tickets, "bilhete_num_id_client");
                String bilhete_num_id_client_bilhetes_clean = TratamentoVariaveisBilheteDB_simples(bilhete_num_id_client_bilhetes);

                String bilhete_num_adult_bilhetes = Dividir_json_porCapitulo_simples(oJson_tickets, "bilhete_num_adult");
                String bilhete_num_adult_bilhetes_clean = TratamentoVariaveisBilheteDB_simples(bilhete_num_adult_bilhetes);

                String bilhete_num_child_bilhetes = Dividir_json_porCapitulo_simples(oJson_tickets, "bilhete_num_child");
                String bilhete_num_child_bilhetes_clean = TratamentoVariaveisBilheteDB_simples(bilhete_num_child_bilhetes);

                String date_come_bilhetes = Dividir_json_porCapitulo_simples(oJson_tickets, "bilhete_date_come");
                String date_come_bilhetes_clean = TratamentoVariaveisBilheteDB_simples(date_come_bilhetes);

                String date_go_bilhetes = Dividir_json_porCapitulo_simples(oJson_tickets, "bilhete_date_go");
                String date_go_bilhetes_clean = TratamentoVariaveisBilheteDB_simples(date_go_bilhetes);

                String date_pay_bilhetes = Dividir_json_porCapitulo_simples(oJson_tickets, "bilhete_date_pay");
                String date_pay_bilhetes_clean = TratamentoVariaveisBilheteDB_simples(date_pay_bilhetes);

                String b_type_trip = Dividir_json_porCapitulo_simples(oJson_tickets, "bilhete_type_trip");
                String b_type_trip_clean = TratamentoVariaveisBilheteDB_simples(b_type_trip);


                String bilhete_id = origem_bilhetes_clean[0];
                String bilhete_ip_client = ip_client_bilhetes_clean;
                String bilhete_num_id_client = bilhete_num_id_client_bilhetes_clean;
                String bilhete_phone_client = bilhete_phone_client_bilhetes_clean;
                String bilhete_name_client = first_name_bilhetes_clean + last_name_bilhetes_clean;
                String bilhete_email_client = bilhete_email_client_bilhetes_clean;
                String bilhete_type_trip = b_type_trip_clean;
                String bilhete_destination_trip = destino_bilhetes_clean;
                String bilhete_source_trip = origem_bilhetes_clean[1];
                String bilhete_num_child = bilhete_num_child_bilhetes_clean;
                String bilhete_num_adult = bilhete_num_adult_bilhetes_clean;
                String bilhete_date_go = date_go_bilhetes_clean;
                String bilhete_date_come = date_come_bilhetes_clean;
                String bilhete_type_pay = p_bilhetes_clean;
                String bilhete_date_pay = date_pay_bilhetes_clean;
                String bilhete_total_pay = bilhete_total_pay_bilhetes_clean;

                bib[i] = new BILHETE(bilhete_id, bilhete_ip_client, bilhete_num_id_client, bilhete_phone_client, bilhete_name_client,
                        bilhete_email_client, bilhete_type_trip, bilhete_destination_trip, bilhete_source_trip, bilhete_num_child, bilhete_num_adult,
                        bilhete_date_go, bilhete_date_come, bilhete_type_pay, bilhete_date_pay, bilhete_total_pay);


            }


        } catch (Exception e) {
            e.printStackTrace();

        }


        return bib;
    }


    public static boolean GetOnlineState(){

        String url = baseUrl + "=get_online_state";

        String state = null;
        try {
            state = GetStringFromURL(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(state.equalsIgnoreCase("ON")){
            return true;
        }

        return false;
    }

    public static BILHETE[] GetValidationTicket() {

        String url = baseUrl + "=get_validation_db";

        BILHETE[] tudo = null;

        try {
            JSONObject oJson = new JSONObject(GetStringFromURL(url));
            JSONObject oJson_tickets = oJson.getJSONObject("bilhetes");

            JSONArray oJson_array_origem = oJson_tickets.getJSONArray("ida_e_volta");
            int count = oJson_array_origem.length();

            String[] tickets_chapater = new String[count];


            for (int i = 0; i < count; i++) {
                tickets_chapater[i] = oJson_array_origem.getString(i);
            }


            tudo = new BILHETE[count];

            for (int i = 0; i < tudo.length; i++) {

                String[] aux_clean = TratamentoValidatorBilheteDB(tickets_chapater[i]);

                String validar_id = aux_clean[0];
                String ida = aux_clean[1];
                String volta = aux_clean[2];
                String post_id = aux_clean[3];


                tudo[i] = new BILHETE(validar_id, ida, volta, post_id);

            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return tudo;

    }


    public static String[] Dividir_json_porCapitulo(JSONObject oJson_tickets, String tipoDados) {
        String[] tickets_chapater = null;

        try {

            // exemplo tipoDados = "origem"
            JSONArray oJson_array_origem = oJson_tickets.getJSONArray(tipoDados);
            int count = oJson_array_origem.length();

            tickets_chapater = new String[count];

            for (int i = 0; i < count; i++) {
                tickets_chapater[i] = oJson_array_origem.getString(i);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tickets_chapater;
    }

    public static String Dividir_json_porCapitulo_simples(JSONObject oJson_tickets, String tipoDados) {
        String tickets_chapter = null;

        try {

            JSONArray oJson_array_origem = oJson_tickets.getJSONArray(tipoDados);
            tickets_chapter = oJson_array_origem.getString(0);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tickets_chapter;
    }

    private static String[] TratamentoVariaveisBilheteDB(String bilhete) {

        String[] array_id_meta_value = new String[2];

        // String [] todos = Helper_APi.GetTicketDataBaseFromApi();

       /* String bilhete = todos[0];
        String bilhete_1 = todos[1];*/

        int startIndex = bilhete.indexOf(":") + 2;
        int endIndex = bilhete.indexOf(",") - 1;

        int startIndex_ = bilhete.indexOf("meta_value") + 13;
        int endIndex_ = bilhete.length() - 2;

        String post_id = bilhete.substring(startIndex, endIndex);
        String meta_value = bilhete.substring(startIndex_, endIndex_);

        array_id_meta_value[0] = post_id;
        array_id_meta_value[1] = meta_value;


        return array_id_meta_value;

    }

    private static String TratamentoVariaveisBilheteDB_simples(String bilhete) {
        String id_meta_value = null;

        if (bilhete != null && bilhete.length() > 0 && !bilhete.equals("null")) {

            int startIndex_ = bilhete.indexOf("meta_value") + 13;
            int endIndex_ = bilhete.length() - 2;

            String meta_value = bilhete.substring(startIndex_, endIndex_);

            id_meta_value = meta_value;

            return id_meta_value;
        } else {

            return id_meta_value = "";
        }

    }

    private static String[] TratamentoValidatorBilheteDB(String bilhete) {

        String[] array_id_meta_value = new String[4];

        try {

            int startIndex = bilhete.indexOf(":") + 2;
            int endIndex = bilhete.indexOf(",") - 1;

            String ida_s = "," + '"' + "ida" + '"' + ":";

            int startIndex_ = bilhete.indexOf(ida_s) + 8;
            int endIndex_ = bilhete.indexOf("volta") - 3;

            int startIndex__ = bilhete.indexOf("volta") + 8;
            int endIndex__ = bilhete.length() - 2;

            int startIndex___ = bilhete.indexOf("validar_id") + 13;
            int endIndex___ = bilhete.indexOf(ida_s) - 1;

            String post_id = bilhete.substring(startIndex, endIndex);
            String ida = bilhete.substring(startIndex_, endIndex_);
            String volta = bilhete.substring(startIndex__, endIndex__);
            String validar_id = bilhete.substring(startIndex___, endIndex___);

            array_id_meta_value[0] = validar_id;
            array_id_meta_value[1] = ida;
            array_id_meta_value[2] = volta;
            array_id_meta_value[3] = post_id;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return array_id_meta_value;

    }

    private static String GetStringFromURL(String url) {
        try {
            return new GetUrlTask().execute(url).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class GetUrlTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            return GetURLString(params);
        }
    }

    public static String GetURLString(String... params) {

        URL newUrl;
        BufferedReader in;
        String inputLine = "";

        try {

            //REtirar os espaços de strings
            String strURLespaços = params[0].replaceAll(" ", "%20");

            //Retirar assentos de strings
            String strURlassentos = removerAcentos(strURLespaços);

            newUrl = new URL(strURlassentos);

            ///////////////////////////
            //só para servidores codificados
            final String username = "programacao";
            final String password = "11274289";
            Authenticator.setDefault(new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password.toCharArray());
                }
            });
            ////////////////

            InputStream a = newUrl.openStream();

            InputStreamReader aux = new InputStreamReader(a);

            in = new BufferedReader(aux);
            String textoLido = "";
            while ((textoLido = in.readLine()) != null) {
                inputLine += textoLido;
            }
            in.close();

        } catch (Exception e) {
            e.fillInStackTrace();

            return null;
        }

        return inputLine;
    }

    private static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    public static boolean FirstTimeOpenApp(Context c){

        final String PREFS_NAME = "MyPrefsFile";

        boolean firsTime = false;

        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");

            // first time task
            firsTime = true;

            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit();
        }

        return firsTime;

    }
}
