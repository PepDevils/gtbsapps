package com.example.dezvezesdez.getbus_android;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by dezvezesdez on 11/05/16.
 */
public class DialogFragment_sign_in extends DialogFragment {

    protected EditText email;
    protected EditText pass1;
    protected EditText pass2;


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

        View v = inflater.inflate(R.layout.dialog_fragment_sign_in, container);

        email = (EditText) v.findViewById(R.id.emailText);
        pass1 = (EditText) v.findViewById(R.id.passordText);
        pass2 = (EditText) v.findViewById(R.id.passordTextRepite);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        (getDialog().getWindow()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        WindowManager.LayoutParams params = (getDialog()).getWindow().getAttributes();
        params.width = (int) (displaymetrics.widthPixels * 0.95);
        params.height = (int) (displaymetrics.heightPixels * 0.60);
        params.gravity = Gravity.CENTER;
        (getDialog().getWindow()).setAttributes(params);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.rounded_dialog);

        v.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


        // LogIn(v);

        Button bt_sign_in = (Button) v.findViewById(R.id.bt_sign_in);
        bt_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register(v);

                // dismiss();
            }
        });

        Button bt_esquecime = (Button) v.findViewById(R.id.bt_esquecime);
        bt_esquecime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EsquecimePalavraPasse(v);

                // dismiss();
            }
        });


        Button bt_sair = (Button) v.findViewById(R.id.bt_sair);
        bt_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return v;
    }


    public void EsquecimePalavraPasse(View v) {

        //falta a documentação no servidor

        //enviar email
        String mail = email.getText().toString();

        // validar o email

        if (mail.isEmpty())
            Toast.makeText(getActivity(), "O campo de email não pode estar vazio.", Toast.LENGTH_SHORT).show();
        else {

            String resultado = Helper_APi.PerdeuPalavraPasse(mail);

            if (resultado != null){
                Toast.makeText(getActivity(), "Foi-lhe enviado um email com a nova palavra passe.", Toast.LENGTH_LONG).show();
                dismiss();
            }


        }
    }

    private void Register(View v) {

        String emailText = email.getText().toString();
        String password = pass1.getText().toString();


        if (!password.equals(pass2.getText().toString())) {
            Toast.makeText(getActivity(), "As palavras passe inseridas não são iguais", Toast.LENGTH_LONG).show();
            pass1.setText("");
            pass2.setText("");
        } else if (emailText.isEmpty() || password.isEmpty()) {
            Toast.makeText(getActivity(), "Por favor, preencha os campos em falta    ", Toast.LENGTH_LONG).show();
        } else {

            // regista e faz log in logo
            boolean registo = Helper_APi.RegisterUser(getActivity(), emailText, password);

            if(registo){
                Helper_LogIn.dataLogin(getActivity(), emailText, password);
            }

            if(Helper_LogIn.isLogedIn()){
                dismiss();
            }

        }

    }
}
