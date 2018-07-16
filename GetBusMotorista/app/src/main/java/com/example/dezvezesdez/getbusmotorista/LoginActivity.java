package com.example.dezvezesdez.getbusmotorista;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dezvezesdez on 15/06/16.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("A Autentificar...");
        progressDialog.show();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onLoginSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        Intent i = new Intent(LoginActivity.this, FragmentActivity_Loading.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Acesso Errado", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);

        this.finish();
    }

    public boolean validate() {
        boolean valid = true;

        String name = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || !name.equalsIgnoreCase("motorista")) {
            _emailText.setError("Nome errado");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("A password tem entre 4 a 10 digitos");
            valid = false;
        }else if(!password.equalsIgnoreCase("motor")) {
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }


}
