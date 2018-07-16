package com.example.dezvezesdez.getbus_android;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.view.ContextThemeWrapper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

/**
 * Created by dezvezesdez on 11/05/16.
 */
public class DialogFragment_seguimento_compra extends DialogFragment {

    protected EditText emailText;
    protected EditText passordText;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Script", "onCreat");


         // setStyle(DialogFragment.STYLE_NO_INPUT, R.style.MyCustomBetterPickersRadialTimePickerDialog);
          setStyle(DialogFragment.STYLE_NORMAL, R.style.MyCustomBetterPickersRadialTimePickerDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.i("Script", "onCreateView");

        View v = inflater.inflate(R.layout.dialog_fragment_sc, container);

        emailText = (EditText) v.findViewById(R.id.emailText);
        passordText = (EditText) v.findViewById(R.id.passordText);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        (getDialog().getWindow()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        WindowManager.LayoutParams params = (getDialog()).getWindow().getAttributes();
        params.width = (int) (displaymetrics.widthPixels * 0.95);
        params.height = (int) (displaymetrics.heightPixels * 0.60);
        params.gravity = Gravity.CENTER;
        (getDialog().getWindow()).setAttributes(params);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.rounded_dialog);
        v.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        LogIn(v);

        Button bt_conta = (Button) v.findViewById(R.id.bt_criar_conta);
        bt_conta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                DialogFragment_sign_in dfsI = new DialogFragment_sign_in();
                dfsI.show(fm, "tag_criar_conta");

            }
        });




        Button bt_sem_conta = (Button) v.findViewById(R.id.bt_sem_conta);
        bt_sem_conta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Activity a = getActivity();
                Intent i = new Intent(a, Activity_Formulario.class);
                i.putExtra("tag_price", Activity_Bilheteira.price);
                i.putExtra("tag_id", Activity_Bilheteira.id_random);
                a.startActivity(i);
                a.finish();

                dismiss();
            }
        });


        return v;
    }



    public void LogIn(View v){

        Button bt_log_in = (Button) v.findViewById(R.id.bt_log_in);
        bt_log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail = emailText.getText().toString();
                String password = passordText.getText().toString();

                Helper_LogIn.dataLogin(getActivity(), mail, password);

                if(Helper_LogIn.isLogedIn()){
                    dismiss();
                }

            }
        });

    }

}
