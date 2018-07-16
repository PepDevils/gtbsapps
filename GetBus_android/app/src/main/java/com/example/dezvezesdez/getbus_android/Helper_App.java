package com.example.dezvezesdez.getbus_android;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.security.MessageDigest;
import java.util.Locale;

/**
 * Created by dezvezesdez on 09/05/16.
 */
public class Helper_App {

    public static String NumberToMonth(Context c, int numM) {
        String Month = null;

        String[] month_array = c.getResources().getStringArray(R.array.string_array_meses);

        switch (numM) {
            case 0:
                Month = month_array[numM];
                break;
            case 1:
                Month = month_array[numM];
                break;
            case 2:
                Month = month_array[numM];
                break;
            case 3:
                Month = month_array[numM];
                break;
            case 4:
                Month = month_array[numM];
                break;
            case 5:
                Month = month_array[numM];
                break;
            case 6:
                Month = month_array[numM];
                break;
            case 7:
                Month = month_array[numM];
                break;
            case 8:
                Month = month_array[numM];
                break;
            case 9:
                Month = month_array[numM];
                break;
            case 10:
                Month = month_array[numM];
                break;
            case 11:
                Month = month_array[numM];
                break;
            case 12:
                Month = month_array[numM];
                break;

        }

        return Month;
    }

    //metodo que muda o tipo de lingua
    public static void setLocale(Activity a, String lang) {
        // http://stackoverflow.com/questions/12908289/how-to-change-language-of-app-when-user-selects-language
        Locale myLocale = new Locale(lang);
        Resources res = a.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        //Intent refresh = new Intent(a, a.getClass()); // para abrir a propria activity
        Intent refresh = new Intent(a, FragmentActivity_Loading.class);  // para abrir a sempre a activity inicial
        a.startActivity(refresh);
        a.finish();
    }



    //Codifica a palavra passe para ser enviada para o servidor
    public static String EncodedPassword(String rawPassword) {
        // Encriptação do tipo SHA-256
        try {
            //Throw error para não usar esta função
            //throw new Exception();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(rawPassword.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            return String.format("%064x", new java.math.BigInteger(1, digest));
        }
        catch (Exception e) {
            return rawPassword;
        }
    }

    public static String getPhoneNumber(Activity a) {
        TelephonyManager tMgr = (TelephonyManager) a.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();
        return mPhoneNumber;
    }


}
