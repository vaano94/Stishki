package com.stishki.iva;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stishki.iva.Helper.RegistrationHelper;

import org.json.JSONObject;

import java.security.AccessControlContext;
import java.util.concurrent.ExecutionException;


public class RegistrationActivity extends AppCompatActivity {
    public RegistrationParameters registrationParameters;
    private EditText nameet;
    private EditText nicket;
    private EditText passwordet;
    private EditText passwordrptet;
    private EditText emailet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameet = (EditText)findViewById(R.id.reg_edit_name);
        nicket = (EditText)findViewById(R.id.reg_edit_nick);
        passwordet = (EditText)findViewById(R.id.reg_edit_pass);
        passwordrptet = (EditText)findViewById(R.id.reg_edit_passrpt);
        emailet = (EditText)findViewById(R.id.reg_edit_email);

        Button registerButton = (Button) findViewById(R.id.reg_regbutton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrationParameters = new RegistrationParameters();
                registrationParameters.execute(
                        nameet.getText().toString(),
                        nicket.getText().toString(),
                        passwordet.getText().toString(),
                        passwordrptet.getText().toString(),
                        emailet.getText().toString()
                );

            }
        });

    }


    public class RegistrationParameters extends AsyncTask<String, Void, String> {


        @Override
        protected void onPostExecute(String s) {
            switch (s) {
                case             "OK": {Toast.makeText(getApplicationContext(), "Регистрация успешна", Toast.LENGTH_SHORT).show();break;}
                case  "NickNameIssue": {Toast.makeText(getApplicationContext(), "Такой ник уже занят", Toast.LENGTH_SHORT).show();break;}
                case     "EmailIssue": {Toast.makeText(getApplicationContext(), "Такой e-mail уже занят", Toast.LENGTH_SHORT).show();break;}
                case  "PasswordIssue": {Toast.makeText(getApplicationContext(), "Введенные пароли не совпадают", Toast.LENGTH_SHORT).show();break;}
                case "EmptyParameter": {Toast.makeText(getApplicationContext(), "Один из требуемых параметров пуст", Toast.LENGTH_SHORT).show();break;}
            }
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... params) {

            String result ="";
            try {
                String name = params[0];
                String nick = params[1];
                String password = params[2];
                String passwordrpt= params[3];
                String email= params[4];

                if (name.equals("") || nick.equals("") || password.equals("") || email.equals("")) {
                    return "EmptyParameter";
                }

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("nickName", nick);
                jsonObject.put("firstName", name);
                jsonObject.put("password", password);
                jsonObject.put("email", email);

                // Checks if passwords are equal
                if (password.equals(passwordrpt)) {

                result = RegistrationHelper.helpRegister(jsonObject);

                }
                else {
                    return "PasswordIssue";
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }

}
