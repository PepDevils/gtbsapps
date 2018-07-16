package com.example.dezvezesdez.getbus_android;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by dezvezesdez on 10/05/16.
 */
public class Helper_APi {

    static String baseUrl = "http://dezvezesdez.eu/getbus/app/mobile_api.php?func";

    public static boolean RegisterUser(Activity a,String email, String password) {
        password = Helper_App.EncodedPassword(password);
        String URL = baseUrl + "=register_user&email=" + email + "&password=" + password + "&phone=" + Helper_App.getPhoneNumber(a);
        String result = GetStringFromURL(URL);

        if(result.equals("Got it!")){
            return true;
        }else{
            Toast.makeText(a,"Verifique o campo de email", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    //LogIn atravéz da api interna
    public static String LogIn(Activity a, String email, String encryptedPassword) {
        String result;

        if (encryptedPassword != "" && email != "") {
            encryptedPassword = Helper_App.EncodedPassword(encryptedPassword);
            String URL = baseUrl + "=get_user_data&email=" + email + "&password=" + encryptedPassword + "&phone=" + Helper_App.getPhoneNumber(a);
            result = GetStringFromURL(URL);
        } else
            result = "Os campos não podem estar vazios";

        return result;
    }

    //O resultado é "" se tiver corrido tudo bem, ou é uma mensagem de erro caso contrário
    public static String PerdeuPalavraPasse(String email) {
        final String APIkey = "qwerty";

        //final String APIlink = "http://carlomonteiro.pt/app/inscricao/mail/smartprocess.php?email=" + email + "&key=" + APIkey;
        final String APIlink = "http://dezvezesdez.eu/getbus/app/inscricao/mail/smartprocess.php?email=" + email + "&key=" + APIkey;
                             // http://dezvezesdez.eu/getbus/app/inscricao/mail/smartprocess.php?email=p.fonseca.21@hotmail.com&key=qwerty

        String result = Helper_APi.GetStringFromURL(APIlink);
        return result;
    }

    public static ArrayList<String> CarregarPartidasDestinos() {
        String url = baseUrl + "=get_spinners_content";
        String todos = GetStringFromURL(url);
        String[] aux = todos.split(", ");
        ArrayList cada = new ArrayList<>(Arrays.asList(aux));
        return cada;

    }

    public static String GetPriceFromAPi(String num_adult, String num_crianca, boolean ida_e_volta) {
        String price;
        String id_fixo = "2";

        if (num_adult.equals("0")){
            num_adult = "null";
        }

        if (num_crianca.equals("0")){
            num_crianca = "null";
        }

        String url = baseUrl + "=get_bilhete" + "&id=" + id_fixo + "&numa=" + num_adult + "&numc=" + num_crianca + "&iev=" + ida_e_volta;
        price = GetStringFromURL(url);

        return price;
    }


    //Recebe a informação em String de uma URL
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
            InputStream a = newUrl.openStream();
            InputStreamReader aux = new InputStreamReader(a);
            in = new BufferedReader(aux);
            String textoLido = "";
            while ((textoLido = in.readLine()) != null) {
                inputLine += textoLido;
            }
            in.close();

        } catch (Exception e) {
            String aux = e.getMessage();
            return null;
        }

        return inputLine;
    }



    //METODO PARA RETIRAR OS ASSENTOS DAS STRINGS
    private static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }


}
