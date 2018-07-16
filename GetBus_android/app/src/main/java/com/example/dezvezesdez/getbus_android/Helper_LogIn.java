package com.example.dezvezesdez.getbus_android;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONObject;

/**
 * Created by dezvezesdez on 11/05/16.
 */
public class Helper_LogIn {
    public static String email = null;
    public static String id = null;
    public static String fullName = null;

    public static boolean isLogedIn() {
        return (email != null);
    }

    public static void logOut() {
        //client.Logout();
        email = null;
        fullName = null;

    }


    public static boolean dataLogin(Activity a, String mail, String passe) {
        if (mail.isEmpty() || passe.isEmpty()) {
            Toast.makeText(a, "Por favor, preencha os campos que faltam", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            String result = Helper_APi.LogIn(a, mail, passe);

            try {
                JSONObject user = new JSONObject(result);
                id = user.getString("ID");
                email = user.getString("email");
                fullName = user.getString("name");

                Intent i = new Intent(a, Activity_Formulario.class);
                i.putExtra("tag_price", Activity_Bilheteira.price);
                i.putExtra("tag_id", Activity_Bilheteira.id_random);
                a.startActivity(i);
                a.finish();

                return true;

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(a, result, Toast.LENGTH_SHORT).show();
                logOut();
                return false;
            }
        }
    }



}
