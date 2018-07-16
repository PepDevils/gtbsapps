package com.example.dezvezesdez.getbusmotorista;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by dezvezesdez on 08/06/16.
 */
public class Receiver_Connection extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, final Intent intent) {

        boolean var = IsConnected(context);
        String vars = Boolean.toString(var);

        Intent i = new Intent("Broad_CastName_Receiver_Connection");
        i.putExtra("message", vars);

        context.sendBroadcast(i);

    }

    protected boolean IsConnected(Context context) {
        boolean isConnected_hardware;
        boolean isConnected_hardware_and_software;

        final ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo net = connManager.getActiveNetworkInfo();
        isConnected_hardware = (net != null && net.isConnected());

        isConnected_hardware_and_software = false;

        if (isConnected_hardware) {

            if(Helper_APi.GetOnlineState()){
                isConnected_hardware_and_software = true;
            }

        }

        return isConnected_hardware_and_software;
    }

}

